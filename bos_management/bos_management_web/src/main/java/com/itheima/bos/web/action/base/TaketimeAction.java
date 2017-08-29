package com.itheima.bos.web.action.base;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.TaketimeService;
import com.itheima.bos.web.action.common.CommonAction;
/**
 * 收派时间管理
 * @author zhaoqx
 *
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class TaketimeAction extends CommonAction<TakeTime> {
	@Autowired
	private TaketimeService service;
	/**
	 * 查询所有收派时间，返回json数据
	 */
	@Action(value="taketimeAction_listajax")
	public String listajax(){
		List<TakeTime> list = service.findAll();
		list2json(list, null);
		return NONE;
	}
}
