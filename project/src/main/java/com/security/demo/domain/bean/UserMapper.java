package com.security.demo.domain.bean;

import lombok.Data;

@Data
public class UserMapper {

    private String username = "nihao";

    private String role = "user";

    private String password = "1234";


    public String getUsername(String src) {
        return username;
    }

    public String getRole(String src) {
        return role;
    }

    public String getPassword(String src) {
        return password;
    }
}
