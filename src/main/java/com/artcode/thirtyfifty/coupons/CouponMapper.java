package com.artcode.thirtyfifty.coupons;

import org.springframework.stereotype.Component;

@Component
public class CouponMapper {

	public CouponResponse couponToResponse(Coupons coupons) {

		CouponResponse response = new CouponResponse();
		response.setId(coupons.getId());
		response.setSchemeName(coupons.getSchemeName());
		response.setFlatRates(coupons.getFlatRates());
		response.setDiscount(coupons.getDiscount());
		response.setType(coupons.getType());
		response.setCode(coupons.getCode());
		response.setStartDate(coupons.getStartDate());
		response.setEndDate(coupons.getEndDate());
		response.setTotalCoupon(coupons.getTotalCoupon());
		response.setMinimumCartValue(coupons.getMinimumCartValue());
		response.setMaximumTime(coupons.getMaximumTime());
		response.setMaximumAmount(coupons.getMaximumAmount());
		response.setFirstOrder(coupons.isFirstOrder());
		response.setStatus(coupons.getStatus());

		return response;
	}

	public Coupons dtoToCoupon(CouponsDto dto) {
		Coupons coupons = new Coupons();
		coupons.setSchemeName(dto.getSchemeName());
		coupons.setCode(dto.getCode());
		coupons.setDiscount(dto.getDiscount());
		coupons.setEndDate(dto.getEndDate());
		coupons.setFlatRates(dto.getFlatRates());
		coupons.setStatus(dto.getStatus());
		coupons.setMaximumAmount(dto.getMaximumAmount());
		coupons.setMaximumTime(dto.getMaximumTime());
		coupons.setMinimumCartValue(dto.getMinimumCartValue());
		coupons.setStartDate(dto.getStartDate());
		coupons.setTotalCoupon(dto.getTotalCoupon());
		coupons.setType(dto.getType());
		coupons.setFirstOrder(dto.isFirstOrder());
		coupons.setDeleted(false);
		coupons.setExpired(false);
		return coupons;
	}
	
	public Coupons updateCoupon(CouponsDto dto, Coupons coupons) {
		coupons.setSchemeName(dto.getSchemeName());
		coupons.setCode(dto.getCode());
		coupons.setDiscount(dto.getDiscount());
		coupons.setEndDate(dto.getEndDate());
		coupons.setFlatRates(dto.getFlatRates());
		coupons.setStatus(dto.getStatus());
		coupons.setMaximumAmount(dto.getMaximumAmount());
		coupons.setMaximumTime(dto.getMaximumTime());
		coupons.setMinimumCartValue(dto.getMinimumCartValue());
		coupons.setStartDate(dto.getStartDate());
		coupons.setTotalCoupon(dto.getTotalCoupon());
		coupons.setType(dto.getType());
		coupons.setFirstOrder(dto.isFirstOrder());
		coupons.setDeleted(false);
		coupons.setExpired(false);
		return coupons;
	}

}
