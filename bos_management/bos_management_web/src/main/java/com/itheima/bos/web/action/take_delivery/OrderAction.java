package com.itheima.bos.web.action.take_delivery;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.dao.take_delivery.OrderDao;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.service.take_delivery.OrderService;
import com.itheima.bos.web.action.common.CommonAction;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class OrderAction extends CommonAction<Order> {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private OrderService service;
	
	//查询为分配的订单
	@Action(value = "orderAction_findByCourierIsNull")
	public String findByCourierIsNull() throws IOException {

		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Order> pb = orderDao.findByCourierIsNull(pageable);
		List<Order> content = pb.getContent();
		for (Order order : content) {
			Date orderTime = order.getOrderTime();
		}
		page2json(pb, new String[] { "workBills", "fixedAreas","sendArea","recArea","courier","wayBill" });
		return NONE;
	}
	
	
	//人工调度分单
	@Action(value="orderAction_dispatcher",results={
			@Result(name="success",type="redirect",location="/pages/take_delivery/dispatcher.jsp")})
	public String dispatcher() throws IOException{
		Order order = orderDao.findById(getModel().getId());
		service.dispatcher(order,getModel().getCourier());
		return SUCCESS;
	}

}
