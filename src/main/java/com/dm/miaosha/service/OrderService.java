package com.dm.miaosha.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dm.miaosha.dao.OrderDao;
import com.dm.miaosha.domain.MiaoshaOrder;
import com.dm.miaosha.domain.MiaoshaUser;
import com.dm.miaosha.domain.OrderInfo;
import com.dm.miaosha.redis.OrderKey;
import com.dm.miaosha.redis.RedisService;
import com.dm.miaosha.vo.GoodsVo;

@Service
public class OrderService {
	@Autowired
	OrderDao orderDao;
	@Autowired
	RedisService redisService;

	public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, long goodsId) {
		// TODO 自动生成的方法存根
//		return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
		return redisService.get(OrderKey.getMiaoshaOrderByUidGid, ""+userId+"_"+goodsId,MiaoshaOrder.class);
	}
	@Transactional
	public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
		// TODO 自动生成的方法存根
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(user.getId());
		orderDao.insert(orderInfo);
		MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
		miaoshaOrder.setGoodsId(goods.getId());
		miaoshaOrder.setOrderId(orderInfo.getId());
		miaoshaOrder.setUserId(user.getId());
		orderDao.insertMiaoshaOrder(miaoshaOrder);
		
		redisService.set(OrderKey.getMiaoshaOrderByUidGid, ""+user.getId()+"_"+goods.getId(),miaoshaOrder);
		return orderInfo;
		
	}

	public OrderInfo getOrderById(long orderId) {
		// TODO 自动生成的方法存根
		return orderDao.getOrderById(orderId);
		
	}
	public void deleteOrders() {
		// TODO 自动生成的方法存根
		orderDao.deleteOrders();
		orderDao.deleteMiaoshaOrders();
	}
	

}
