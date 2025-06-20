package com.artcode.thirtyfifty.mock_exam.question;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.artcode.thirtyfifty.mock_exam.question.options.QuestionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("mock-exam-question")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MockExamQuestion {

    private String question;
    
    private List<QuestionOption> questionOptions;
    
    private String correctAnswer;
}
