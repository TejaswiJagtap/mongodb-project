package com.artcode.thirtyfifty.master.subcategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryDto {

	private String id;

	private String name;

	private String categoryId;

	private String categoryName;
	
}
