package com.artcode.thirtyfifty.master.category;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String>{

	Category findByIdAndIsDeleted(String id, boolean isDeleted);

	Category findByLevelId(String levelId);

}
