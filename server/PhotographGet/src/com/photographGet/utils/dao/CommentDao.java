package com.photographGet.utils.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDao {

	@Resource
	private SessionFactory sessionFactory;
}
