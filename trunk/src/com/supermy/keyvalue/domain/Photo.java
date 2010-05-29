package com.supermy.keyvalue.domain;

import java.io.Serializable;

/**
 * @author my
 *
 */
public class Photo extends Base {

	private static final long serialVersionUID = 1L;
	private String name;

	public Photo() {
	}

	public Photo(String name, String id) {
		this.name = name;
		this.id = id;
	}

	public Photo(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
