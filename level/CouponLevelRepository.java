package com.artcode.thirtyfifty.coupons.level;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponLevelRepository extends JpaRepository<CouponLevel, String>,
		PagingAndSortingRepository<CouponLevel, String>, JpaSpecificationExecutor<CouponLevel> {

	List<CouponLevel> findByCouponsId(String CouponsId);

	List<CouponLevel> deleteByCouponsId(String CouponsId);

}
