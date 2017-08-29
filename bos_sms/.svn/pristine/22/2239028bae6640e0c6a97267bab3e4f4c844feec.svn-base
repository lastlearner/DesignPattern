package com.itheima.mq.consumer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

import com.itheima.mq.utils.SmsUtils;

@Component
public class CustomeListener implements MessageListener{
	//监听方法，当监听的队列中存在消息时，这个方法自动执行，读取消息
	public void onMessage(Message message) {
		MapMessage mapMessage = (MapMessage)message;
		try {
			String telephone = mapMessage.getString("telephone");
			String msg = mapMessage.getString("msg");
			System.out.println("消息监听器读取到了新消息：" + telephone + " " + msg);
			SmsUtils.sendSmsByWebService(telephone, msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
