package com.smartkb.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("knowledge_document")
public class KnowledgeDocumentDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("name")
    private String name;
    @TableField("file_type")
    private String fileType;
    @TableField("file_size")
    private Long fileSize;
    @TableField("file_path")
    private String filePath;
    @TableField("chunk_count")
    private Integer chunkCount;
    @TableField("status")
    private String status;
    @TableField("created_time")
    private LocalDateTime createdTime;
}