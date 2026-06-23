package com.smartkb.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("prompt_template")
public class PromptTemplateDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("name")
    private String name;
    @TableField("description")
    private String description;
    @TableField("system_prompt")
    private String systemPrompt;
    @TableField("category")
    private String category;
    @TableField("match_keywords")
    private String matchKeywords;
    @TableField("match_mode")
    private Integer matchMode;
    @TableField("is_preset")
    private Integer isPreset;
    @TableField("is_active")
    private Integer isActive;
    @TableField("sort_order")
    private Integer sortOrder;
    @TableField("created_time")
    private LocalDateTime createdTime;
    @TableField("updated_time")
    private LocalDateTime updatedTime;
}