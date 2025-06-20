package com.artcode.thirtyfifty.constants;

public enum StatusConstant {

	MALE("MALE"), 
	FEMALE("FEMALE"), 
	OTHER("OTHER"),

	ACTIVE("ACTIVE"), 
	DEACTIVE("DEACTIVE"),
	
	USER_TYPE_ADMIN("ADMIN"), 
	USER_TYPE_ADMINISTRATOR("ADMINISTRATOR"), 
	USER_TYPE_USER("USER"), 

	SESSION_PREVIOUS("PREVIOUS"), 
	SESSION_CURRENT("CURRENT"), 
	SESSION_NEXT("NEXT"), 
	SESSION_OLD("OLD"),

	IS_ACTIVE_YES("YES"), 
	IS_ACTIVE_NO("NO"), 
	IS_ACTIVE_ALL("ALL"),
	YES("YES"), 
	NO("NO"),

	ENQUIRY_STATUS_LOST("LOST"), 
	ENQUIRY_STATUS_ACTIVE("ACTIVE"), 
	ENQUIRY_STATUS_WON("WON"),
	ENQUIRY_STATUS_NO_RESPONSE("NO RESPONSE"), 
	ENQUIRY_STATUS_REGISTRATION("REGISTRATION"),

	SUNDAY("Sunday"),
	MONDAY("Monday"), 
	TUESDAY("Tuesday"), 
	WEDNESDAY("Wednesday"), 
	THURSDAY("Thursday"),
	FRIDAY("Friday"), 
	SATURDAY("Saturday"),

	MAIL_SEND_TO_ALL("ALL"), 
	MAIL_SEND_TO_SELECTED("SELECTED"),

	CHECK_IN("Check In"), 
	CHECK_OUT("Check Out"),

	MOTHER("Mother"), 
	FATHER("Father"),

	ALBUM_IMAGE("Image"), 
	ALBUM_VIDEO("Video");
	
	private String status;

	private StatusConstant(String status) {
		this.status = status;
	}

	public String value() {
		return status;
	}

}
