package com.artcode.thirtyfifty.coupons.level.category;

import org.springframework.data.mongodb.core.mapping.Document;

import com.artcode.thirtyfifty.coupons.level.CouponLevel;
import com.artcode.thirtyfifty.master.category.Category;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponCategory {

	@Id
	private String id;
	
	@ManyToOne
	private CouponLevel couponLevel;
	
	@OneToOne
	private Category category;
}
