package com.artcode.thirtyfifty.coupons;

public enum CouponStatus {

	ACTIVE("ACTIVE"), 
	DEACTIVE("DEACTIVE");

	private String status;

	private CouponStatus(String status) {
		this.status = status;
	}

	public String value() {
		return status.toString();
	}
}
