package com.smartkb.service.impl;

import com.smartkb.entity.model.KnowledgeDocumentDO;
import com.smartkb.mapper.KnowledgeDocumentMapper;
import com.smartkb.model.dto.response.DocumentVO;
import com.smartkb.service.DocumentService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    private static final Logger log = LoggerFactory.getLogger(DocumentServiceImpl.class);
    private static final String UPLOAD_DIR = "./data/documents";
    private static final int MAX_CHUNK_SIZE = 500;
    private static final int MAX_OVERLAP = 50;

    private final KnowledgeDocumentMapper docMapper;
    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore embeddingStore;

    public DocumentServiceImpl(KnowledgeDocumentMapper docMapper,
                               EmbeddingModel embeddingModel,
                               EmbeddingStore embeddingStore) {
        this.docMapper = docMapper;
        this.embeddingModel = embeddingModel;
        this.embeddingStore = embeddingStore;
    }

    @Override
    public DocumentVO upload(MultipartFile file) {
        try {
            // 1. Save file
            KnowledgeDocumentDO doc = new KnowledgeDocumentDO();
            doc.setName(file.getOriginalFilename());
            String ext = getExtension(file.getOriginalFilename());
            doc.setFileType(ext);
            doc.setFileSize(file.getSize());
            doc.setStatus("PROCESSING");
            doc.setCreatedTime(LocalDateTime.now());
            doc.setChunkCount(0);
            docMapper.insert(doc);

            Path dir = Path.of(UPLOAD_DIR, String.valueOf(doc.getId()));
            Files.createDirectories(dir);
            Path filePath = dir.resolve(file.getOriginalFilename());
            file.transferTo(filePath.toFile());
            doc.setFilePath(filePath.toString());

            // 2. Parse text
            String text = parseText(filePath.toFile(), ext);
            if (text == null || text.isBlank()) {
                doc.setStatus("FAILED");
                doc.setChunkCount(0);
                docMapper.updateById(doc);
                return toVO(doc);
            }

            // 3. Split into chunks
            Document langDoc = Document.from(text);
            DocumentByParagraphSplitter splitter = new DocumentByParagraphSplitter(MAX_CHUNK_SIZE, MAX_OVERLAP);
            List<TextSegment> segments = splitter.split(langDoc);

            // 4. Embed and store
            dev.langchain4j.model.output.Response<List<dev.langchain4j.data.embedding.Embedding>> embedResp = embeddingModel.embedAll(segments);
            List<dev.langchain4j.data.embedding.Embedding> embeddings = embedResp.content();
            for (int i = 0; i < segments.size(); i++) {
                TextSegment seg = TextSegment.from(segments.get(i).text());
                seg.metadata().put("document_id", String.valueOf(doc.getId()));
                seg.metadata().put("document_name", doc.getName());
                embeddingStore.add(embeddings.get(i), seg);
            }

            // 5. Update DB
            doc.setChunkCount(segments.size());
            doc.setStatus("READY");
            docMapper.updateById(doc);

            log.info("Document processed: id={}, chunks={}", doc.getId(), segments.size());
            return toVO(doc);
        } catch (Exception e) {
            log.error("Document upload failed", e);
            throw new RuntimeException("Document processing failed: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DocumentVO> list() {
        return docMapper.selectList(null).stream().map(this::toVO).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        KnowledgeDocumentDO doc = docMapper.selectById(id);
        if (doc == null) return;
        // Clean up file
        if (doc.getFilePath() != null) {
            try { Files.deleteIfExists(Path.of(doc.getFilePath())); } catch (Exception ignored) {}
        }
        docMapper.deleteById(id);
        // Note: embedding store cleanup is not supported by InMemoryEmbeddingStore
    }

    private String parseText(File file, String ext) {
        try {
            // Use Tika for parsing
            org.apache.tika.Tika tika = new org.apache.tika.Tika();
            return tika.parseToString(file);
        } catch (Exception e) {
            log.warn("Tika parse failed, fallback to plain text: {}", file.getName());
            try {
                return Files.readString(file.toPath());
            } catch (Exception ex) {
                return null;
            }
        }
    }

    private String getExtension(String name) {
        if (name == null || !name.contains(".")) return "unknown";
        return name.substring(name.lastIndexOf('.') + 1).toLowerCase();
    }

    private DocumentVO toVO(KnowledgeDocumentDO d) {
        DocumentVO vo = new DocumentVO();
        vo.setId(d.getId()); vo.setName(d.getName()); vo.setFileType(d.getFileType());
        vo.setFileSize(d.getFileSize()); vo.setChunkCount(d.getChunkCount());
        vo.setStatus(d.getStatus()); vo.setCreatedTime(d.getCreatedTime());
        return vo;
    }
}