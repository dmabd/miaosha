package com.dm.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dm.miaosha.dao.UserDao;
import com.dm.miaosha.domain.User;



@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	public User getById(int id) {
		 return userDao.getById(id);
	}

	public User tx(int i) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Transactional
	public boolean tx() {
		User u1= new User();
		u1.setId(2);
		u1.setName("2222");
		userDao.insert(u1);
		
		User u2= new User();
		u2.setId(1);
		u2.setName("11111");
		userDao.insert(u2);
		
		return true;
	}
	
}
