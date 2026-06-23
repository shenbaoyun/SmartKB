package com.smartkb.service;

import com.smartkb.entity.model.ModelConfigDO;
import com.smartkb.model.dto.response.ModelConfigVO;
import java.util.List;

public interface ModelConfigService {
    List<ModelConfigVO> listActive();
    ModelConfigDO getByCode(String code);
}