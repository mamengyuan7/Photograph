package com.photographGet.utils.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.photographGet.entity.User;


@Repository
public class UserDao {

	@Resource
	private SessionFactory sessionFactory;
	//����û�
	public void saveUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(user);
	}
	//����id��ȡ�û���Ϣ
	public User findUser(int id) {
		Session session=this.sessionFactory.getCurrentSession();
		User user=new User();
		user=session.get(User.class, id);
		return user;
	}
	//���ݵ绰�����ѯ��Ϣ
	public User findUserbyTelephone(String telephone) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("from User where telephone = ?");
		query.setParameter(0, telephone);
		User user = (User) query.uniqueResult();
		System.out.println("name"+user.getTelephone());
		return user;
	}
	//�г������û�
	public List<User> findAllUser(){
		Session session=this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from User");
		return query.list();
	}
	
	
}
