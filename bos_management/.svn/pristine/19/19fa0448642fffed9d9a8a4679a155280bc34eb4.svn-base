package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.FixedArea;

public interface FixedAreaService {

	public void save(FixedArea model);

	public Page<FixedArea> pageQuery(Pageable pageable);

	public void associationCourier2FixedArea(String id, Integer courierId, Integer takeTimeId);


	public List<FixedArea> findAll();



	public FixedArea findById(String fixedAreaId);


}
