package com.itheima.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierDao;
import com.itheima.bos.dao.base.FixedAreaDao;
import com.itheima.bos.dao.base.TaketimeDao;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.FixedAreaService;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService{
	@Autowired
	private FixedAreaDao dao;
	
	@Autowired
	private CourierDao courierDao;
	
	@Autowired
	private TaketimeDao taketimeDao;
	
	public void save(FixedArea model) {
		dao.save(model);
	}
	
	public Page<FixedArea> pageQuery(Pageable pageable) {
		return dao.findAll(pageable);
	}

	/**
	 * 定区关联快递员
	 */
	public void associationCourier2FixedArea(String id, Integer courierId, Integer takeTimeId) {
		FixedArea fixedArea = dao.findOne(id);
		Courier courier = courierDao.findOne(courierId);
		TakeTime takeTime = taketimeDao.findOne(takeTimeId);
		fixedArea.getCouriers().add(courier);//建立定区和快递员的关联关系
		courier.setTakeTime(takeTime);//建立快递员和收派时间关联关系
	}


	@Override
	public List<FixedArea> findAll() {
		return dao.findAll();
	}



	@Override
	public FixedArea findById(String fixedAreaId) {
		
		return dao.findOne(fixedAreaId);
	}

}
