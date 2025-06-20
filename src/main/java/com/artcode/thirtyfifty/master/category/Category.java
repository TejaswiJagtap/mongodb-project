package com.artcode.thirtyfifty.master.category;

import org.springframework.data.mongodb.core.mapping.Document;

import com.artcode.thirtyfifty.master.level.Level;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category {

	@Id
	private String id;

	private String name;

	private Level level;

	private boolean isDeleted;

	public Category(String id) {
		super();
		this.id = id;
	}
}
