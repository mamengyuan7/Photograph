package com.photographGet.utils.dao;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.photographGet.entity.PictureDetail;

@Repository
public class PicDetailDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public void savePictureDetail(PictureDetail pictureDetail) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(pictureDetail);
	}
	
	public PictureDetail findByFlagAndId(int picId,int flag) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from PictureDetail where picId= ? and flag= ?");
		query.setParameter(0, picId);
		query.setParameter(1, flag);
		PictureDetail pictureDetail = (PictureDetail)query.uniqueResult();
		return pictureDetail;
	}
}
