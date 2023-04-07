package com.sherm.pfinance.dto;
import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
    private String message;
    private Map<String, Object> additionalInfo;

    public ErrorResponse(String message) {
        //this.status = status;
        this.message = message;
        this.additionalInfo = new HashMap<>();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Map<String, Object> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void addAdditionalInfo(String key, Object value) {
        this.additionalInfo.put(key, value);
    }


}




