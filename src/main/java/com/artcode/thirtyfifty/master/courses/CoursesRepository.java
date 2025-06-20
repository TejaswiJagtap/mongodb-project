package com.artcode.thirtyfifty.master.courses;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CoursesRepository extends MongoRepository<Courses, String>{

	Optional<Courses> findByIdAndIsDeleted(String id, boolean isDeleted);

	Courses findBySubCategoryId(String subCategoryId);

}
