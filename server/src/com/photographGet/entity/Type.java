/**
 * @author binghua
 * @version 1.0
 * @Description:
 * @date:datedate{time}
 */
package com.photographGet.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author ff
 *
 */
@Entity
@Table(name = "type")
public class Type {
	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name = "increment_generator",strategy = "increment")
	private int id;
	
	private String type;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
