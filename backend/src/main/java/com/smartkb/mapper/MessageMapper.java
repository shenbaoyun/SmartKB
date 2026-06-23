package com.smartkb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartkb.entity.model.MessageDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<MessageDO> {
    List<MessageDO> selectByConversationId(@Param("conversationId") Long conversationId,
                                           @Param("limit") Integer limit);
    int deleteByConversationId(@Param("conversationId") Long conversationId);
}