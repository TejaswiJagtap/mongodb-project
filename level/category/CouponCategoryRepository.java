package com.artcode.thirtyfifty.coupons.level.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponCategoryRepository extends JpaRepository<CouponCategory, Long>,
		PagingAndSortingRepository<CouponCategory, Long>, JpaSpecificationExecutor<CouponCategory> {

	List<CouponCategory> findByCouponLevelId(Long couponLevelId);
	
	List<CouponCategory> deleteByCouponLevelId(Long couponLevelId);

}
