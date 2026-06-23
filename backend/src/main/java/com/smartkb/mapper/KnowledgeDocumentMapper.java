package com.smartkb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartkb.entity.model.KnowledgeDocumentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface KnowledgeDocumentMapper extends BaseMapper<KnowledgeDocumentDO> {
    List<KnowledgeDocumentDO> selectByStatus(@Param("status") String status);
}