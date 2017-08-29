package com.itheima.mq.consumer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.springframework.stereotype.Component;
import com.itheima.mq.utils.MailUtils;

@Component
public class SendMailListener implements MessageListener{

	//监听方法，当监听队列中出现消息时，自动执行，并发送
	public void onMessage(Message message) {
		MapMessage mapMessage = (MapMessage)message;
		try {
			String subject = mapMessage.getString("subject");
			String content = mapMessage.getString("content");
			String to = mapMessage.getString("to");
			System.out.println("subject : "+ subject); 
			System.out.println("content : "+ content); 
			System.out.println("to : "+ to); 
			MailUtils.sendMail(subject, content, to);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
		/*try {
			String telephone = mapMessage.getString("telephone");
			String msg = mapMessage.getString("msg");
			System.out.println("消息监听器读取了新消息123："+telephone+"  "+msg);
			SmsUtils.sendSmsByWebService(telephone, msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}*/
	}
}
















