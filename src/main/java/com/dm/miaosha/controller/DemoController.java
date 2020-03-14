package com.dm.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dm.miaosha.domain.User;
import com.dm.miaosha.rabbitmq.MQSender;
import com.dm.miaosha.redis.RedisService;
import com.dm.miaosha.redis.UserKey;
import com.dm.miaosha.result.CodeMsg;
import com.dm.miaosha.result.Result;
import com.dm.miaosha.service.UserService;

@Controller
@RequestMapping("/demo")
public class DemoController {
//本类主要进行配置上的测试
	@Autowired
	UserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	MQSender sender;
	
//	
//	@RequestMapping("/rabbit")
//	@ResponseBody
//	public Result<String> mq() {
//		sender.send("hello dm");
//		return Result.success("hello,dm");
//	}
//	@RequestMapping("/rabbit/topic")
//	@ResponseBody
//	public Result<String> topic() {
//		sender.sendTopic("hello dm");
//		return Result.success("hello,dm");
//	}
//	@RequestMapping("/rabbit/header")
//	@ResponseBody
//	public Result<String> header() {
//		sender.sendHeader("hello dm");
//		return Result.success("hello,dm");
//	}


	// 1.rest api json 输出 2.页面
	@RequestMapping("/hello")
	@ResponseBody
	public Result<String> hello() {
		return Result.success("hello,dm");
	}

	@RequestMapping("/helloError")
	@ResponseBody
	public Result<String> helloError() {
		return Result.error(CodeMsg.SERVER_ERROR);
		// return new Result(500102, "XXX");
	}

	@RequestMapping("/thymeleaf")
	public String thymeleaf(Model model) {
		model.addAttribute("name", "dm");
		return "hello";
	}

	@RequestMapping("/db/get")
	@ResponseBody
	public Result<User> dbGet() {
		User user = userService.getById(1);
		return Result.success(user);

	}

	@RequestMapping("/db/tx")
	@ResponseBody
	public Result<Boolean> dbTx() {
		User user = userService.tx(1);
		return Result.success(true);

	}

	@RequestMapping("/redis/get")
	@ResponseBody
	public Result<User> redisGet() {
		User user = redisService.get(UserKey.getById, "" + 1, User.class);
		return Result.success(user);
	}
	
    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
    	User user  = new User();
    	user.setId(1);
    	user.setName("1111");
    	redisService.set(UserKey.getById, ""+1, user);//UserKey:id1
        return Result.success(true);
    }
    
	

}
