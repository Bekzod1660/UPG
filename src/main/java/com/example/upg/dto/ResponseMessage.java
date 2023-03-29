package com.example.upg.dto;

public enum ResponseMessage {
    SUCCESS("transaction is success",0),
    ERROR_PRODUCT_NOT_FOUND("product not found", -100),
    ERROR_USER_NOT_FOUND("user not found", -200),
    ERROR_USER_ALREADY_EXIST("user already exist", -201);


    private String message;
    private int statusCode;

    ResponseMessage(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
