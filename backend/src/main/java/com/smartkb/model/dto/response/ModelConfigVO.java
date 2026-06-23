package com.smartkb.model.dto.response;

import java.time.LocalDateTime;

/**
 * 模型配置视图对象
 *
 * @author codex
 * @since 1.0.0
 */
public class ModelConfigVO {
    private Long id;
    private String name;
    private String code;
    private String modelName;
    private Integer isActive;
    private Integer sortOrder;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }
    public Integer getIsActive() { return isActive; }
    public void setIsActive(Integer isActive) { this.isActive = isActive; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
}
