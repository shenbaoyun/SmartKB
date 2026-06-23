package com.smartkb.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("model_config")
public class ModelConfigDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("name")
    private String name;
    @TableField("code")
    private String code;
    @TableField("base_url")
    private String baseUrl;
    @TableField("api_key")
    private String apiKey;
    @TableField("model_name")
    private String modelName;
    @TableField("is_active")
    private Integer isActive;
    @TableField("sort_order")
    private Integer sortOrder;
    @TableField("created_time")
    private LocalDateTime createdTime;
    @TableField("updated_time")
    private LocalDateTime updatedTime;
}