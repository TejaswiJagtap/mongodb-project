package com.artcode.thirtyfifty.coupons.level;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponLevelRepository extends MongoRepository<CouponLevel, String>{

	List<CouponLevel> findByCouponsId(String couponId);

	void deleteByCouponsId(String couponId);

}
