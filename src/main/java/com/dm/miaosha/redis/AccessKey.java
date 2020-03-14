package com.dm.miaosha.redis;

public class AccessKey extends BasePrefix {

	public AccessKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
		
	}
	public static AccessKey Access=new AccessKey(5, "access");
	public static AccessKey withExpire(int expireSeconds) {
		return new AccessKey(expireSeconds, "access");
	}
}
