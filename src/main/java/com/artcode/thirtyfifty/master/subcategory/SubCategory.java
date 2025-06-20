package com.artcode.thirtyfifty.master.subcategory;

import org.springframework.data.mongodb.core.mapping.Document;

import com.artcode.thirtyfifty.master.category.Category;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("sub-category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategory {

	@Id
	private String id;

	private String name;

	private Category category;

	private boolean isDeleted;

	public SubCategory(String id) {
		super();
		this.id = id;
	}

}
