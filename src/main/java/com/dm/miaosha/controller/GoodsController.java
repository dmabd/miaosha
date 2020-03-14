package com.dm.miaosha.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.dm.miaosha.domain.MiaoshaUser;
import com.dm.miaosha.redis.GoodsKey;
import com.dm.miaosha.redis.RedisService;
import com.dm.miaosha.result.Result;
import com.dm.miaosha.service.GoodsService;
import com.dm.miaosha.service.MiaoshaUserService;
import com.dm.miaosha.vo.GoodsDetailVo;
import com.dm.miaosha.vo.GoodsVo;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	@Autowired
	MiaoshaUserService miaoshaUserService;
	@Autowired
	RedisService redisService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	ThymeleafViewResolver thymeleafViewResolver;
	@Autowired
	ApplicationContext applicationContext;
	
//	@RequestMapping("/to_list")
//	public String toList(Model model,HttpServletResponse response,
//			@CookieValue(value=MiaoshaUserService.COOKIE_NAME_TOKEN,required=false) String cookieToken,
//			@RequestParam(value=MiaoshaUserService.COOKIE_NAME_TOKEN,required=false) String paramToken) {
//		if(StringUtils.isEmpty(paramToken)&&StringUtils.isEmpty(cookieToken)) {
//			return "login";
//		}
//		String token=StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//		MiaoshaUser user = miaoshaUserService.getByToken(response,token);
//		model.addAttribute("user",user);
//		return "goods_list";
//	}
    @RequestMapping(value="/to_list", produces="text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model,MiaoshaUser user) {
    	model.addAttribute("user", user);
    	//取缓存
    	String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
    	if(!StringUtils.isEmpty(html)) {
    		return html;
    	}
    	List<GoodsVo> goodsList = goodsService.listGoodsVo();
    	model.addAttribute("goodsList", goodsList);
//    	 return "goods_list";
    	SpringWebContext ctx = new SpringWebContext(request,response,
    			request.getServletContext(),request.getLocale(), model.asMap(), applicationContext );
    	//手动渲染
    	html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
    	if(!StringUtils.isEmpty(html)) {
    		redisService.set(GoodsKey.getGoodsList, "", html);
    	}
    	return html;
    }
    
    @RequestMapping(value="/detail2/{goodsId}",produces="text/html")
    @ResponseBody
    public String detail2(Model model,MiaoshaUser user,HttpServletRequest request, HttpServletResponse response,
    		@PathVariable("goodsId")long goodsId) {
    	model.addAttribute("user", user);
    	
    	//取缓存
    	String html = redisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);
    	if(!StringUtils.isEmpty(html)) {
    		return html;
    	}
    	//手动渲染
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	model.addAttribute("goods", goods);

    	
    	int miaoshaStatus=0;
    	int remianseconds=0;
    	int remainstop=0;
    	long startAt = goods.getStartDate().getTime();
    	long endAt = goods.getEndDate().getTime();
    	long now =System.currentTimeMillis();
    	if(now<startAt) { //秒杀未开始
    		miaoshaStatus=0;
    		remianseconds=(int) ((startAt-now)/1000);
    	}else if(now>endAt){//秒杀结束
    		miaoshaStatus=2;
    		remianseconds=-1;
    		
    	}else {//秒杀正在进行
    		miaoshaStatus=1;
    		remianseconds=0;
    		remainstop=(int) ((endAt-now)/1000);
    	}
    	model.addAttribute("miaoshaStatus",miaoshaStatus);
    	model.addAttribute("remainSeconds", remianseconds); 
    	model.addAttribute("remainstop", remainstop);
//    	return "goods_detail";
    	
    	SpringWebContext ctx = new SpringWebContext(request,response,
    			request.getServletContext(),request.getLocale(), model.asMap(), applicationContext );
    	html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
    	if(!StringUtils.isEmpty(html)) {
    		redisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html);
    	}
    	return html;
    	
    }
    @RequestMapping(value="/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(HttpServletRequest request, HttpServletResponse response, Model model,MiaoshaUser user,
    		@PathVariable("goodsId")long goodsId) {
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	long startAt = goods.getStartDate().getTime();
    	long endAt = goods.getEndDate().getTime();
    	long now = System.currentTimeMillis();
    	int miaoshaStatus = 0;
    	int remainSeconds = 0;
    	if(now < startAt ) {//秒杀还没开始，倒计时
    		miaoshaStatus = 0;
    		remainSeconds = (int)((startAt - now )/1000);
    	}else  if(now > endAt){//秒杀已经结束
    		miaoshaStatus = 2;
    		remainSeconds = -1;
    	}else {//秒杀进行中
    		miaoshaStatus = 1;
    		remainSeconds = 0;
    	}
    	GoodsDetailVo vo = new GoodsDetailVo();
    	vo.setGoods(goods);
    	vo.setUser(user);
    	vo.setRemainSeconds(remainSeconds);
    	vo.setMiaoshaStatus(miaoshaStatus);
    	return Result.success(vo);
    }


}
