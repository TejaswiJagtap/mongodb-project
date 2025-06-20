package com.artcode.thirtyfifty.coupons.level.category.subcategory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponSubCategoryRepository extends JpaRepository<CouponSubCategory, Long>,
		PagingAndSortingRepository<CouponSubCategory, Long>, JpaSpecificationExecutor<CouponSubCategory> {

	List<CouponSubCategory> findByCouponCategoryId(Long couponCategoryId);
	
	List<CouponSubCategory> deleteByCouponCategoryId(Long couponCategoryId);

}
