package com.artcode.thirtyfifty.coupons.level.category;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponCategoryRepository extends MongoRepository<CouponCategory, String>{

	List<CouponCategory> findByCouponLevelId(String id);

	void deleteByCouponLevelId(String id);

}
