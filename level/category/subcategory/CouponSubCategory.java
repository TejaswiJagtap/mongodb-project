package com.artcode.thirtyfifty.coupons.level.category.subcategory;

import com.artcode.thirtyfifty.coupons.level.category.CouponCategory;
import com.artcode.thirtyfifty.master.subcategory.SubCategory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponSubCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "coupon_category_id")
	private CouponCategory couponCategory;

	@OneToOne
	@JoinColumn(name = "sub_category_id")
	private SubCategory subCategory;
}
