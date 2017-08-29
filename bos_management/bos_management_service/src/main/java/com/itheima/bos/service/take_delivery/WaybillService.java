package com.itheima.bos.service.take_delivery;

import java.util.List;

import com.itheima.bos.domain.take_delivery.WayBill;

public interface WaybillService {

	public void save(List<WayBill> list);

	public WayBill findByWayBillNum(String wayBillNum);

	public List<WayBill> findAll();

	public void save(WayBill model);

}
