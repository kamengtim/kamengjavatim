package com.citytuike.mapper;

import org.apache.ibatis.annotations.Param;

import com.citytuike.model.TpFreightConfig;

public interface TpOrderMapper {

	TpFreightConfig findByTemId(@Param(value="template_id")Integer template_id);


}
