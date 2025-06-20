package com.artcode.thirtyfifty.coupons.level.category;

import java.util.List;

import com.artcode.thirtyfifty.coupons.level.category.subcategory.CouponSubCategoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponCategoryDto {

	private Long categoryId;

	private List<CouponSubCategoryDto> couponSubCategoryDtos;
}
