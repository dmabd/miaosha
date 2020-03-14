package com.dm.miaosha.service;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dm.miaosha.Exception.GlobalException;
import com.dm.miaosha.domain.MiaoshaUser;
import com.dm.miaosha.domain.MiaoshaUserDao;
import com.dm.miaosha.redis.MiaoshaUserKey;
import com.dm.miaosha.redis.RedisService;
import com.dm.miaosha.result.CodeMsg;
import com.dm.miaosha.util.MD5Util;
import com.dm.miaosha.util.UUIDUtil;
import com.dm.miaosha.vo.LoginVo;



@Service
public class MiaoshaUserService {
	public static final String COOKIE_NAME_TOKEN="token";
	
	@Autowired
	MiaoshaUserDao miaoshaUserDao;
	
	@Autowired
	RedisService redisService;
	
	
	public MiaoshaUser getById(long id) {
		//取缓存
		MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, ""+id, MiaoshaUser.class);
		if(user!=null) {
			return user;
		}
		//取数据库
		user=miaoshaUserDao.getById(id);
		if(user!=null) {
			redisService.set(MiaoshaUserKey.getById, ""+id, user);
		}
		return user;
	}
	public boolean updatePassword(String token, long id, String formPass) {
		//取user
		MiaoshaUser user = getById(id);
		if(user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		//更新数据库
		MiaoshaUser toBeUpdate = new MiaoshaUser();
		toBeUpdate.setId(id);
		toBeUpdate.setPassword(MD5Util.FromPassToDBPass(formPass, user.getSalt()));
		miaoshaUserDao.update(toBeUpdate);
		//处理缓存
		redisService.delete(MiaoshaUserKey.getById, ""+id);
		user.setPassword(toBeUpdate.getPassword());
		redisService.set(MiaoshaUserKey.token, token, user);
		return true;
	}
	
	public String login(HttpServletResponse response,LoginVo loginvo) {
		if(loginvo==null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		String mobile=loginvo.getMobile();
		String formPass=loginvo.getPassword();
		//判断手机号是否存在
		MiaoshaUser user = getById(Long.parseLong(mobile));
		if(user==null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		//验证密码
		String dbPass=user.getPassword();
		String saltDB=user.getSalt();
		String calcPass=MD5Util.FromPassToDBPass(formPass, saltDB);
		if(!calcPass.equals(dbPass)) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
		//生成cookie
		String token =UUIDUtil.uuid();
		addCookie(response, token, user);
		return token;
		
	}

	public MiaoshaUser getByToken(HttpServletResponse response,String token) {
		// TODO 自动生成的方法存根
		
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
		//延长有限期
		if(miaoshaUser!=null) {
			addCookie(response, token,miaoshaUser);
		}
		
		return miaoshaUser;
		
	}
	private void addCookie(HttpServletResponse response,String token,MiaoshaUser user) {		
		redisService.set(MiaoshaUserKey.token, token, user);
		Cookie cookie=new Cookie(COOKIE_NAME_TOKEN,token);
		cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
		
	}


}
