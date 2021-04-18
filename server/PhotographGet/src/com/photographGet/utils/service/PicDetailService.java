package com.photographGet.utils.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photographGet.entity.PictureDetail;
import com.photographGet.utils.dao.PicDetailDao;

@Service
public class PicDetailService {

	@Resource
	PicDetailDao picDetailDao;
	
	@Transactional(readOnly = false)
	public void savePicDetail(PictureDetail pictureDetail) {
		this.picDetailDao.savePictureDetail(pictureDetail);
	}
	
	@Transactional(readOnly = false)
	public PictureDetail findByFlagAndId(int picId,int flag) {
		return this.picDetailDao.findByFlagAndId(picId, flag);
	}
}
