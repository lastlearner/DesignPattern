package com.itheima.bos.dao.take_delivery;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.take_delivery.Order;

public interface OrderDao extends JpaRepository<Order, Integer> {
	//查询为分单的订单
	Page<Order> findByCourierIsNull(Pageable pageable);
	
	//查询订单
	Order findById(Integer id);

	Order findByOrderNum(String orderNum);

}
