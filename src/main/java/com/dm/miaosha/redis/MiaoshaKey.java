package com.dm.miaosha.redis;

public class MiaoshaKey extends BasePrefix{

	
	
	private MiaoshaKey(int expireSeconds,String prefix) {
		super(expireSeconds,prefix);
	}
	public static MiaoshaKey isGoodsOver = new MiaoshaKey(0,"go");
	public static KeyPrefix getMiaoshaPath = new MiaoshaKey(60,"mp");
	public static final KeyPrefix getMiaoshaVerifyCode = new MiaoshaKey(600,"mvc");
}
