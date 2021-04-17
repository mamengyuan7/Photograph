package com.photographGet.utils.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photographGet.entity.Picture;
import com.photographGet.utils.dao.PictureDao;

@Service
public class PictureService {

	@Resource
	private PictureDao pictureDao;
	
	@Transactional(readOnly = false)
	public void savePicture(Picture p) {
		this.pictureDao.savePicture(p);
	}
	
	@Transactional(readOnly = false)
	public List<Picture> findAllPicture(){
		return this.pictureDao.findAllPicture();
	}
	
	@Transactional(readOnly = false)
	public List<Picture> findByUserId(int userId){
		return this.pictureDao.findByUserId(userId);
	}
                @Transactional(readOnly = false)
	public Picture findById(int id){
		return this.pictureDao.findById(id);
	}
}
