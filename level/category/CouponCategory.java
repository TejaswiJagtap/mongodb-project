package com.artcode.thirtyfifty.coupons.level.category;

import com.artcode.thirtyfifty.coupons.level.CouponLevel;
import com.artcode.thirtyfifty.master.category.Category;

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
public class CouponCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "coupon_level_id")
	private CouponLevel couponLevel;
	
	@OneToOne
	@JoinColumn(name = "category_id")
	private Category category;
 }
