package com.dm.miaosha.result;

//code信息
public class CodeMsg {
	
	
	
	private int code;
	private String msg;
	
	//通用异常
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
	public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常:%s");
	public static CodeMsg REQUST_ILLEGAL = new CodeMsg(500102, "请求非法");
	//登录模块 5002XX
	public static CodeMsg SESSION_ERROR = new CodeMsg(500200, "Session不存在或者已经失效");
	public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500201, "密码不能为空");
	public static CodeMsg MOBILE_EMPTY = new CodeMsg(500202, "手机号不能为空");
	public static CodeMsg MOBILE_ERROR = new CodeMsg(500203, "手机号格式错误");
	public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500204, "用户不存在");
	
	public static CodeMsg PASSWORD_ERROR = new CodeMsg(500205, "密码错误");
	//商品模块 5003XX
	
	//订单模块 5004XX
	public static CodeMsg ORDER_NOT_EXIST= new CodeMsg(500401, "订单不存在");
	
	//秒杀模块 5005XX
	public static final CodeMsg ACCESS_LIMIT = new CodeMsg(500500, "访问太频繁");
	
	public static CodeMsg MIAO_SHA_OVER = new CodeMsg(500501, "商品已售空");
	public static CodeMsg MIAO_SHA_REPEAT = new CodeMsg(500502, "禁止重复秒杀");
	public static CodeMsg MIAO_SHA_FAIL = new CodeMsg(500503, "秒杀失败");
	private CodeMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}
	public CodeMsg fillArgs(Object...args) {
		int code=this.code;
		String message=String.format(this.msg, args);
		return new CodeMsg(code,message);
	}
	@Override
	public String toString() {
		return "CodeMsg [code=" + code + ", msg=" + msg + "]";
	}

	public String getMsg() {
		return msg;
	}
}