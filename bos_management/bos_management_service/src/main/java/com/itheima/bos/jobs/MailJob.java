package com.itheima.bos.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.bos.dao.take_delivery.WorkbillDao;
import com.itheima.bos.domain.take_delivery.WorkBill;
import com.itheima.bos.utils.MailUtils;

/**
 * 自定义Job，实现定时发送邮件
 * @author zhaoqx
 *
 */
public class MailJob {
	@Autowired
	private WorkbillDao workbillDao;
	
	public void sendMail(){
		System.out.println("发送邮件了，时间为：" + new Date());
		String subject = "工单信息["+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"]";//主题
		String content = "工单编号    取件状态    生成时间    快递员 <br>";
		String to = "test@itcast.cn";//收件人
		List<WorkBill> list = workbillDao.findAll();
		for (WorkBill workBill : list) {
			content += workBill.getId() + "  " + workBill.getPickstate() + "  " + workBill.getBuildtime() + "  " + workBill.getCourier().getName() + "<br>";
		}
		MailUtils.sendMail(subject, content, to);
	}
}
