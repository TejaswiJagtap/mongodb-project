package com.artcode.thirtyfifty.mock_exam;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MockExamRepository extends MongoRepository<MockExam, String> {

	Optional<MockExam> findByIdAndIsDeleted(String id, boolean isDeleted);

}
