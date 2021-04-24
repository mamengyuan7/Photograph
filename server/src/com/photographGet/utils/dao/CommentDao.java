package com.photographGet.utils.dao;


import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.photographGet.entity.Comment;

@Repository
public class CommentDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public void saveComment(Comment comment) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(comment);
	}
	
	public List<Comment> findBypicID(int picId){
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Comment where picId= ?");
		query.setParameter(0, picId);
		return query.list();
	}
}
