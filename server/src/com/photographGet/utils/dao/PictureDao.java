package com.photographGet.utils.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.photographGet.entity.Picture;

@Repository
public class PictureDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public void savePicture(Picture picture) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(picture);
	}
	
	public List<Picture> findAllPicture(){
		Session session=this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Picture");
		return query.list();
	}
	
	public List<Picture> findByUserId(int userId){
		Session session=this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Picture where userId= ?");
		query.setParameter(0,userId);
		List<Picture> pictures=query.list();
		return pictures;
	}
	public Picture findById(int id){
		Session session=this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Picture where id= ?");
		query.setParameter(0,id);
		Picture picture=(Picture)query.uniqueResult();
		return picture;
	}
	//根据typeid查找图片
	public List<Picture> findByTypeId(int typeId){
		Session session=this.sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Picture where typeId= ?");
		query.setParameter(0,typeId);
		List<Picture> pictures=query.list();
		return pictures;
	}
}
