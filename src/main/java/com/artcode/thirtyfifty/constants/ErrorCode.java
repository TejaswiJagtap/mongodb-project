package com.artcode.thirtyfifty.constants;

public enum ErrorCode {

	C100("C100","Mobile Number not found into the System, Please register mobile number"),
	C101("C101","Exception in OTP Sending to your register mobile number"),
	C102("C102","Request from unauthorized host"),
	C103("C103","Invalid Otp"),
	C104("C104","Mobile Number And Patient Name already exists into the System, Please register with other mobile number or patient name"),
	C105("C105","No schedule added for OPD"),
	C106("C106","OPD Closed on selected date"),
	C107("C107","Cannot add more doctor, Out of limit"),
	C108("C108","Not subscribed to any plan"),
	C109("C109","Record Not find by Id"),
	C110("C110","Unauthorized request"),
	C111("C111","Apointment not found"),
	C112("C112","Subscription plan yet not active"),
	C113("C113","Your old password doesn't matched"),
	C114("C114","This type of image already exists"),
	C115("C115", "Opd is closed on this time, Select time between opning period"),
	C116("C116", "No Sms setting added"),
	C117("C117", "Not subscribed to sms plan to login with otp"),
	C118("C118", "Expense not found , id :"),
	C119("C119", "Expense deleted Failed"),
	C120("C120", "ExpenseHead Head not found , id :"),
	C121("C121", "ExpenseHead deleted Failed"),
	C122("C122", "Available slot is :"),
	C123("C123","Already registered with this mobile number, Please register with another mobile number"),
	C124("C124","Role didn't found"),
	C125("C125","Add Subscription Trail plan"),
	C126("C126","File name is null"),
	C127("C127","Patient not registered"),
	C128("C128","Link has been expired, for any information please contact your doctor"),
	C999("C999","Technical Error !");
	
    private  String errorCodes;

    private  String message;
    
	private ErrorCode(String errorCode, String message) {
		this.errorCodes = errorCode;
		this.message = message;
	}

	public String getErrorCode() {
		return errorCodes;
	}

	public String getMessage() {
		return message;
	}
    
    
}

