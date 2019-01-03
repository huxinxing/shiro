package com.security.demo.domain.dto.response;

import lombok.Data;

@Data
public class ResponseResult {

    private String status;

    private String code;

    private String message;

    private String data;

    public ResponseResult(String status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ResponseResult(String status, String code, String message, String data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
