package com.smartkb.service;

import com.smartkb.model.dto.response.DocumentVO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    DocumentVO upload(MultipartFile file);
    List<DocumentVO> list();
    void delete(Long id);
}