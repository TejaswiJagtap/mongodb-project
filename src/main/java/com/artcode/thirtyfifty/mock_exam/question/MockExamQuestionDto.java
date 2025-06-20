package com.artcode.thirtyfifty.mock_exam.question;

import java.util.List;

import com.artcode.thirtyfifty.mock_exam.question.options.QuestionOptionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MockExamQuestionDto {

	private String question;

	private String correctAnswer;

	private List<QuestionOptionDto> questionOptionDtos;
}
