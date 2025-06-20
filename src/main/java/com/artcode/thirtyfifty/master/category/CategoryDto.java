package com.artcode.thirtyfifty.master.category;

import lombok.Data;

@Data
public class CategoryDto {

	private String id;

	private String name;
	
	private String levelId;
	
	private String levelName;

	public CategoryDto() {
		super();
	}

	public CategoryDto(String id, String name, String levelId, String levelName) {
		super();
		this.id = id;
		this.name = name;
		this.levelId = levelId;
		this.levelName = levelName;
	}
	
	
}
