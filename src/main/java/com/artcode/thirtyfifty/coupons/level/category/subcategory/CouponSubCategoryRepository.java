package com.artcode.thirtyfifty.coupons.level.category.subcategory;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponSubCategoryRepository extends MongoRepository<CouponSubCategory, String>{

	void deleteByCouponCategoryId(String id);

	List<CouponSubCategory> findByCouponCategoryId(String id);

}
