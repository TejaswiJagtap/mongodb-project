package com.artcode.thirtyfifty.coupons.level.category.subcategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponSubCategoryResponse {

	private String id;
	
	private String subCategoryId;

	private String subCategory;
	
	private String couponCategoryId;
	
	private String couponCategory;
}
