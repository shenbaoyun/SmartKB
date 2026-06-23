package com.smartkb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartkb.entity.model.ConversationDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConversationMapper extends BaseMapper<ConversationDO> {
}