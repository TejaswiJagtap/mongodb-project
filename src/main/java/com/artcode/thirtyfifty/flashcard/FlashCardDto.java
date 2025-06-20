package com.artcode.thirtyfifty.flashcard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlashCardDto {

	private String id;

	private String question;

	private String answer;

	private String coursesId;
	
	private String coursesName;
}
