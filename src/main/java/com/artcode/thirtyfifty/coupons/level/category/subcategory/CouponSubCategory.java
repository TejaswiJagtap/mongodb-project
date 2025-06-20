package com.artcode.thirtyfifty.coupons.level.category.subcategory;

import org.springframework.data.mongodb.core.mapping.Document;

import com.artcode.thirtyfifty.coupons.level.category.CouponCategory;
import com.artcode.thirtyfifty.master.subcategory.SubCategory;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("coupon-sub-category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponSubCategory {

	@Id
	private String id;

	@ManyToOne
	private CouponCategory couponCategory;

	@OneToOne
	private SubCategory subCategory;
}
