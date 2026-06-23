package com.smartkb.service.impl;

import com.smartkb.common.exception.BusinessException;
import com.smartkb.common.exception.ErrorCodeEnum;
import com.smartkb.entity.model.ModelConfigDO;
import com.smartkb.mapper.ModelConfigMapper;
import com.smartkb.model.dto.response.ModelConfigVO;
import com.smartkb.service.ModelConfigService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelConfigServiceImpl implements ModelConfigService {
    private final ModelConfigMapper modelConfigMapper;
    public ModelConfigServiceImpl(ModelConfigMapper modelConfigMapper) {
        this.modelConfigMapper = modelConfigMapper;
    }
    @Override
    public List<ModelConfigVO> listActive() {
        return modelConfigMapper.selectActiveModels().stream().map(m -> {
            ModelConfigVO vo = new ModelConfigVO();
            vo.setId(m.getId()); vo.setName(m.getName()); vo.setCode(m.getCode());
            vo.setModelName(m.getModelName()); vo.setIsActive(m.getIsActive());
            vo.setSortOrder(m.getSortOrder());
            return vo;
        }).collect(Collectors.toList());
    }
    @Override
    public ModelConfigDO getByCode(String code) {
        ModelConfigDO m = modelConfigMapper.selectByCode(code);
        if (m == null) throw new BusinessException(ErrorCodeEnum.MODEL_NOT_FOUND);
        return m;
    }
}