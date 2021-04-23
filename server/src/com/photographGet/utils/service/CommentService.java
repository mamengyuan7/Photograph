package com.photographGet.utils.service;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photographGet.entity.Comment;
import com.photographGet.utils.dao.CommentDao;

@Service
public class CommentService {

	@Resource
	private CommentDao commentDao;
	
	@Transactional(readOnly = false)
	public void saveComment(Comment comment) {
		this.commentDao.saveComment(comment);
	}
	
	@Transactional(readOnly = false)
	public List<Comment> findBypicID(int picId){
		return this.commentDao.findBypicID(picId);
	}
}
