package com.citytuike.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citytuike.mapper.TpOrderMapper;
import com.citytuike.model.TpFreightConfig;

@Service
public class TpOrderServiceImpl implements TpOrderService{
	@Autowired
	private TpOrderMapper tpOrderMapper;

	public TpFreightConfig findByTemId(Integer template_id) {
		return tpOrderMapper.findByTemId(template_id);
	}

	

}
