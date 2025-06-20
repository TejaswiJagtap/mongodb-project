package com.artcode.thirtyfifty.coupons;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.artcode.thirtyfifty.coupons.level.CouponLevel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupons {

	@Id
	private String id;

	private String schemeName;

	private String flatRates;

	private double discount;

	private String Type;

	private String code;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate startDate;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate endDate;

	private double totalCoupon;

	private double minimumCartValue;

	private double maximumTime;

	private double maximumAmount;

	private boolean firstOrder;

	@Enumerated(EnumType.STRING)
	private CouponStatus status;

	private boolean isDeleted;

	private boolean expired;
	
	private List<CouponLevel> couponLevels;

	public Coupons(String id) {
		super();
		this.id = id;
	}

}
