package com.smartkb.controller;

import com.smartkb.model.dto.response.ApiResponse;
import com.smartkb.model.dto.response.DocumentVO;
import com.smartkb.service.DocumentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ApiResponse<DocumentVO> upload(@RequestParam("file") MultipartFile file) {
        return ApiResponse.success(documentService.upload(file));
    }

    @GetMapping
    public ApiResponse<List<DocumentVO>> list() {
        return ApiResponse.success(documentService.list());
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        documentService.delete(id);
        return ApiResponse.success(null);
    }
}