package com.photographGet.utils.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photographGet.entity.User;
import com.photographGet.utils.dao.UserDao;

@Service
public class UserService {
	
	@Resource
	private UserDao userDao;
	//添加用户
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		this.userDao.saveUser(user);
	}
	//根据id获取用户信息
	@Transactional(readOnly = false)
	public User getUser(int id) {
		return this.userDao.findUser(id);
	}
	@Transactional(readOnly = false)
	public User getUserbyTelephone(String telephone) {
		return this.userDao.findUserbyTelephone(telephone);
	}
	//列出所有的用户
	@Transactional(readOnly = false)
	public List<User> list() {
		return this.userDao.findAllUser();
	}

}
