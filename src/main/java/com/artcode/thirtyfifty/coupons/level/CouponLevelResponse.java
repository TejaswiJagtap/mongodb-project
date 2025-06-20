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

	private String id;
	
	private String levelId;

	private String levelName;
	
	private String couponId;
	
	private String couponName;

	private List<CouponCategoryResponse> couponCategoryResponses;
}
