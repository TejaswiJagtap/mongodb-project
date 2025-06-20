package com.artcode.thirtyfifty.master.subcategory;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubCategoryRepository extends MongoRepository<SubCategory, String>{

	Optional<SubCategory> findByIdAndIsDeleted(String id, boolean isDeleted);

	SubCategory findByCategoryId(String categoryId);

}
