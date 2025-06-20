package com.artcode.thirtyfifty.flashcard;

import org.springframework.data.mongodb.core.mapping.Document;

import com.artcode.thirtyfifty.master.courses.Courses;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("flashcard")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlashCard {

	@Id
	private String id;
	
	private String question;
	
	private String answer;
	
	@ManyToOne
	private Courses courses;
	
	private boolean isDeleted;
}
