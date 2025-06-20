package com.artcode.thirtyfifty.mock_exam.question.options;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("question-options")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionOption {

    private String optionText;

    private boolean isCorrect;
}
