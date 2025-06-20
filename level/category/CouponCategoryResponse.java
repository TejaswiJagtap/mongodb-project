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

	private Long id;

	private Long couponLevelId;

	private String couponLevelName;

	private Long categoryId;

	private String categoryName;

	private List<CouponSubCategoryResponse> couponSubCategoryResponses;
}
