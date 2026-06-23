package com.smartkb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartkb.entity.model.ModelConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ModelConfigMapper extends BaseMapper<ModelConfigDO> {
    List<ModelConfigDO> selectActiveModels();
    ModelConfigDO selectByCode(@Param("code") String code);
}