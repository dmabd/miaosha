package com.dm.miaosha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dm.miaosha.dao.GoodsDao;
import com.dm.miaosha.domain.Goods;
import com.dm.miaosha.domain.MiaoshaGoods;
import com.dm.miaosha.vo.GoodsVo;

@Service
public class GoodsService {
	
	@Autowired
	GoodsDao goodsDao;
	
	public List<GoodsVo> listGoodsVo(){
		return goodsDao.listGoodsVo();
	}

	public GoodsVo getGoodsVoByGoodsId(long goodsId) {
		// TODO 自动生成的方法存根
		return goodsDao.getGoodsVoByGoodsId(goodsId);
		
	}

	public boolean reduceStock(GoodsVo goods) {
		MiaoshaGoods g=new MiaoshaGoods();
		g.setGoodsId(goods.getId());
		int ret=goodsDao.reduceStock(g);
		return ret>0;
		
		
	}

	public void resetStock(List<GoodsVo> goodsList) {
		for(GoodsVo goods : goodsList ) {
			MiaoshaGoods g = new MiaoshaGoods();
			g.setGoodsId(goods.getId());
			g.setStockCount(goods.getStockCount());
			goodsDao.resetStock(g);
		}
	}
	

}
