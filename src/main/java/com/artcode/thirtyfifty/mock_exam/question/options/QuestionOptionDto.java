package com.artcode.thirtyfifty.mock_exam.question.options;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionOptionDto {

	private String optionText;

	private boolean isCorrect;

}
