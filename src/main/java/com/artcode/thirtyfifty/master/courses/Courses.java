package com.artcode.thirtyfifty.master.courses;

import org.springframework.data.mongodb.core.mapping.Document;

import com.artcode.thirtyfifty.master.subcategory.SubCategory;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Courses {

	@Id
	private String id;

	private String name;

	private String description;
	
	private double cost;

    @ManyToOne
    private SubCategory subCategory;

	private boolean isDeleted;

	public Courses(String id) {
		super();
		this.id = id;
	}
	
	

}
