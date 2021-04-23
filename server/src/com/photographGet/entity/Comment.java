package com.photographGet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "comment")
public class Comment {
	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name = "increment_generator",strategy = "increment")
	private int id;
	@Column(name = "user_id")
	private int userId;
	private String content;
	@Column(name = "picture_id")
	private int picId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPicId() {
		return picId;
	}
	public void setPicId(int picId) {
		this.picId = picId;
	}
	
}
