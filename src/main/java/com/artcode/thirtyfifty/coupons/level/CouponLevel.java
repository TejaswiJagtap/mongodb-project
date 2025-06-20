package com.artcode.thirtyfifty.coupons.level;

import org.springframework.data.mongodb.core.mapping.Document;

import com.artcode.thirtyfifty.coupons.Coupons;
import com.artcode.thirtyfifty.master.level.Level;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("coupon-level")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponLevel {
	
	@Id
	private String id;

	@ManyToOne
	private Coupons coupons;

	@OneToOne
	private Level level;

}
