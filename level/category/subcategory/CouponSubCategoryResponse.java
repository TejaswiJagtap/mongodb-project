package com.artcode.thirtyfifty.coupons.level.category.subcategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponSubCategoryResponse {

	private Long id;

	private Long couponCategoryId;

	private String couponCategory;

	private Long subCategoryId;

	private String subCategory;
}
