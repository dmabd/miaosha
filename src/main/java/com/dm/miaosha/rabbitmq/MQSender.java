package com.dm.miaosha.rabbitmq;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dm.miaosha.redis.RedisService;



@Service
public class MQSender {

	private static Logger log = LoggerFactory.getLogger(MQSender.class);
	
	@Autowired
	AmqpTemplate amqpTemplate ;
	
//	public void send(Object message) {
//		String msg = RedisService.beanToString(message);
//		log.info("send message:"+msg);
//		amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
//	}
//	public void sendTopic(Object message) {
//		String msg = RedisService.beanToString(message);
//		log.info("send message:"+msg);
//		
//		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key1" ,msg);
//		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key2" ,msg);
//	}
//	public void sendFanout(Object message) {
//		String msg = RedisService.beanToString(message);
//		log.info("send Fanout message:"+msg);
//		
//		amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE,msg);
//
//	}
//	public void sendHeader(Object message) {
//		String msg = RedisService.beanToString(message);
//		log.info("send Header message:"+msg);
//		MessageProperties properties=new MessageProperties();
//		properties.setHeader("header1", "value1");
//		properties.setHeader("header2", "value2");
//		Message obj=new Message(msg.getBytes(),properties);
//		
//		amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE,"",obj);
//
//	}

	public void sendMiaoShaMessage(MiaoshaMessage mm) {
		// TODO 自动生成的方法存根
		String msg = RedisService.beanToString(mm);
		log.info("send message:"+msg);
		amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, msg);
	}


	


	
	
}
