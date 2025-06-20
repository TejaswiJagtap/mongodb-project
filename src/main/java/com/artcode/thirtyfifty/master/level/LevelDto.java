package com.artcode.thirtyfifty.master.level;

import lombok.Data;

@Data
public class LevelDto {

	private String id;

	private String name;

	private String image;

	private String shortName;

	private boolean isDeleted;

	public LevelDto(String id, String name,String image, String shortName) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.shortName = shortName;
	}

	public LevelDto() {
		super();
	}
	
	
}
