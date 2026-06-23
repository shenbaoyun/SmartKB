package com.smartkb.controller;

import com.smartkb.model.dto.response.ApiResponse;
import com.smartkb.model.dto.response.ModelConfigVO;
import com.smartkb.service.ModelConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模型配置控制器
 *
 * @author codex
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/models")
public class ModelConfigController {

    private final ModelConfigService modelConfigService;

    public ModelConfigController(ModelConfigService modelConfigService) {
        this.modelConfigService = modelConfigService;
    }

    /** 获取所有可用模型 */
    @GetMapping
    public ApiResponse<List<ModelConfigVO>> list() {
        return ApiResponse.success(modelConfigService.listActive());
    }
}
