package com.artcode.thirtyfifty.coupons.level;

import java.util.List;

import com.artcode.thirtyfifty.coupons.level.category.CouponCategoryResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponLevelResponse {

	private Long id;

	private Long couponId;

	private String couponName;

	private Long levelId;

	private String levelName;
	
	private List<CouponCategoryResponse> couponCategoryResponses;
}
