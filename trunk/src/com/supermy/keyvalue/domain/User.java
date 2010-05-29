package com.supermy.keyvalue.domain;

import java.io.Serializable;

/**
 * @author my
 * 
 * 
 */
public class User extends Base {

	private static final long serialVersionUID = 1L;
	private String name;
	private Photo photo;

	public User() {
	}

	public User(String name, String id) {
		this.name = name;
		this.id = id;
	}

	public User(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the photo
	 */
	public Photo getPhoto() {
		return photo;
	}

	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	
}
