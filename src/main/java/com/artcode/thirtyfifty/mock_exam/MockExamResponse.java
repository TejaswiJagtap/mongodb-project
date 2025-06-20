package com.artcode.thirtyfifty.mock_exam;

import java.util.List;

import com.artcode.thirtyfifty.mock_exam.question.MockExamQuestionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MockExamResponse {

	private String id;
	
	private String name;

	private String description;

	private int duration; // duration in minutes
	
    private int totalQuestions;
		
	private String coursesId;
	
	private String coursesName;
	
	private List<MockExamQuestionDto> mockExamQuestionDtos;
}
