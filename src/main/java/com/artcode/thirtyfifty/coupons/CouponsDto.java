package com.artcode.thirtyfifty.coupons;

import java.time.LocalDate;
import java.util.List;

import com.artcode.thirtyfifty.coupons.level.CouponLevelDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponsDto {

	private String schemeName;

	private String flatRates;

	private double discount;

	private String type;

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

	private CouponStatus status;

	private boolean isDeleted;

	private boolean isExpired;

	private List<CouponLevelDto> couponLevelDtos;

}
