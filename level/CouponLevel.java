package com.artcode.thirtyfifty.coupons.level;

import com.artcode.thirtyfifty.coupons.Coupons;
import com.artcode.thirtyfifty.master.level.Level;

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
public class CouponLevel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "coupons_id")
	private Coupons coupons;
	
	@OneToOne
	@JoinColumn(name = "level_id")
	private Level level;

}
