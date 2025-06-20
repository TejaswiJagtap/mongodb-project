package com.artcode.thirtyfifty.master.courses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursesDto {

	private String id;

	private String name;
	
	private String description;
	
	private double cost;

	private String subCategoryId;
	
	private String subCategoryName;
}
