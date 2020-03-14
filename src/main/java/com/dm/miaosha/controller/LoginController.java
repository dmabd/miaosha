package com.dm.miaosha.controller;



import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dm.miaosha.redis.RedisService;
import com.dm.miaosha.result.Result;
import com.dm.miaosha.service.MiaoshaUserService;
import com.dm.miaosha.vo.LoginVo;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	
	private static Logger log=LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	MiaoshaUserService miaoshaUserService;
	@Autowired
	RedisService redisService;
	
	@RequestMapping("/to_login")
	public String toLogin() {
		return "login";
	}
	
//	@RequestMapping("/do_login")
//	@ResponseBody
//	public Result<Boolean> doLogin(LoginVo loginvo ) {
//		log.info(loginvo.toString());
//		//参数校验
//		String passinput=loginvo.getPassword();
//		String mobile=loginvo.getMobile();
//		if(StringUtils.isEmpty(passinput)) {
//			return Result.error(CodeMsg.PASSWORD_EMPTY);
//		}
//		if(StringUtils.isEmpty(mobile)) {
//			return Result.error(CodeMsg.MOBILE_EMPTY);
//		}
//		if(!ValidatorUtil.isMobile(mobile)) {
//			return Result.error(CodeMsg.MOBILE_ERROR);
//		}
//		//登录
//		CodeMsg cm=miaoshaUserService.login(loginvo);
//		if(cm.getCode()==0) {
//			return Result.success(true);
//		}else {
//			return Result.error(cm);
//		}
//		
//	}
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
    	log.info(loginVo.toString());
    	//登录
    	String token =miaoshaUserService.login(response, loginVo);
    	return Result.success(token);
    }
}
