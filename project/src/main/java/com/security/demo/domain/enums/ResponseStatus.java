package com.security.demo.domain.enums;

public enum ResponseStatus {

    SUCCESS("0","请求成功"),
    Default("-1","请求失败");

    private String key;

    private String value;

    ResponseStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return key;
    }

    public String getValue(){
        return value;
    }
}
