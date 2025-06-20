package com.artcode.thirtyfifty.master.level;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("level")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Level {
	
	@Id
	private String id;
	
	private String name;
	
	private String image;
	
	private String shortName;
		
	private boolean isDeleted;

	public Level(String id) {
		super();
		this.id = id;
	}
	
	
	
}
