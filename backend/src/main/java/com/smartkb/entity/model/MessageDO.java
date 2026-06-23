package com.smartkb.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("message")
public class MessageDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("conversation_id")
    private Long conversationId;
    @TableField("role")
    private String role;
    @TableField("content")
    private String content;
    @TableField("prompt_id")
    private Long promptId;
    @TableField("prompt_name")
    private String promptName;
    @TableField("model_code")
    private String modelCode;
    @TableField("created_time")
    private LocalDateTime createdTime;
}