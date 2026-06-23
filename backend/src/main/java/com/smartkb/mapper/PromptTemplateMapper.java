package com.smartkb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartkb.entity.model.PromptTemplateDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PromptTemplateMapper extends BaseMapper<PromptTemplateDO> {
    List<PromptTemplateDO> selectActiveTemplates();
    List<PromptTemplateDO> selectMatchableTemplates();
    PromptTemplateDO selectDefaultTemplate();
}