package com.artcode.thirtyfifty.coupons.level.category;

import java.util.List;

import com.artcode.thirtyfifty.coupons.level.category.subcategory.CouponSubCategoryResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponCategoryResponse {

	private String id;
	
	private String categoryId;

	private String categoryName;

	private String couponLevelId;
	
	private String couponLevelName;
	
	private List<CouponSubCategoryResponse> couponSubCategoryResponses;
}
