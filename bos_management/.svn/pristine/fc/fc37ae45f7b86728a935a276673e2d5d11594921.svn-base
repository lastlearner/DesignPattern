package com.itheima.bos.service.take_delivery.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.take_delivery.WaybillDao;
import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.take_delivery.WaybillService;

@Service
@Transactional
public class WaybillServiceImpl implements WaybillService{
	@Autowired
	private WaybillDao dao;
	public void save(WayBill model) {
		dao.save(model);
	}
	@Override
	public WayBill findByWayBillNum(String wayBillNum) {
		return dao.findByWayBillNum(wayBillNum);
	}
	@Override
	public void save(List<WayBill> list) {
		for (WayBill wayBill : list) {
			dao.save(wayBill);
		}
	}
	@Override
	public List<WayBill> findAll() {
		return dao.findAll();
	}
}
