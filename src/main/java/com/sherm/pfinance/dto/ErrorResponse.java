package com.sherm.pfinance.dto;



public class ErrorResponse {

    private String message;
    private String details;
    
    public ErrorResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }
    
    // getters and setters
}



