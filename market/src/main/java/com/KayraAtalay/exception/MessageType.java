package com.KayraAtalay.exception;

import lombok.Getter;

@Getter
public enum MessageType {

	NO_RECORD_EXIST("1003", "No records found"),
	GENERAL_EXCEPTION("9999", "There is an error"),
	INVALID_STATUS_CHANGE("1004", "Status change is not valid"), TOKEN_EXPIRED("1005", "Token is expired"),
	WRONG_TOKEN("1006", "This token is not exist"),
	USERNAME_NOT_FOUND("1007", "Could not find the username"),
	USERNAME_ALREADY_EXISTS("1008", "This username is already exists"),
	USERNAME_OR_PASSWORD_INVALID("1009", "Wrong username or password"),
	REFRESH_TOKEN_NOT_FOUND("1010", "Could not find the refresh token"),
	REFRESH_TOKEN_EXPIRED("1011", "This refresh token is expired"),
	ADDRESS_NOT_FOUND("1012", "Could not find the address"),
	CUSTOMER_ALREADY_EXISTS("1013", "This customer already exists"), 
	UNAUTHORIZED_ACCESS("1014", "Unauthorized access"),
	CUSTOMER_NOT_FOUND("1015", "Customer information for the logged-in user was not found"),
	CATEGORY_ALREADY_EXISTS("1016", "This category already exists"),
	CATEGORY_NOT_FOUND("1017", "Could not find the category");
	 
	 
	private String code;

	private String message;

	MessageType(String code, String message) {
		this.code = code;
		this.message = message;

	}

}