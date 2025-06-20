package com.artcode.thirtyfifty.constants;

public enum SMSTemplate {

	OTP("1207162195601568012");

	private String status;

	private SMSTemplate(String status) {
			this.status = status;
		}

	public String value() {
		return status.toString();
	}
}
