package com.smartkb.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("conversation")
public class ConversationDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("title")
    private String title;
    @TableField("model_code")
    private String modelCode;
    @TableField("prompt_id")
    private Long promptId;
    @TableField("prompt_mode")
    private String promptMode;
    @TableField("created_time")
    private LocalDateTime createdTime;
    @TableField("updated_time")
    private LocalDateTime updatedTime;
}
