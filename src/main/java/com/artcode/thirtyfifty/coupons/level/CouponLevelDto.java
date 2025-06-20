package com.artcode.thirtyfifty.coupons.level;

import java.util.List;

import com.artcode.thirtyfifty.coupons.level.category.CouponCategoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponLevelDto {

	private String levelId;

	private List<CouponCategoryDto> couponCategoryDtos;

}
