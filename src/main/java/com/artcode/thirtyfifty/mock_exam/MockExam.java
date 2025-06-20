package com.artcode.thirtyfifty.mock_exam;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.artcode.thirtyfifty.master.courses.Courses;
import com.artcode.thirtyfifty.mock_exam.question.MockExamQuestion;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("mock-exam")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MockExam {

	@Id
	private String id;

	private String name;

	private String description;

	private int duration; // duration in minutes
	
    private int totalQuestions;
	
	@ManyToOne
	private Courses courses;

	private List<MockExamQuestion> mockExamQuestions;
	
	private boolean isDeleted;

	public MockExam(String id) {
		super();
		this.id = id;
	}
	
	
}
