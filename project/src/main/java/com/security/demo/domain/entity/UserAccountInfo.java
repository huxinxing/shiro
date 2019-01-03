package com.security.demo.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_account_info")
public class UserAccountInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "country_code")
    private Integer countryCode;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "security_password")
    private String securityPassword;

    @Column(name = "salt")
    private String salt;

    @Column(name = "statu")
    private Integer statu;

    @Column(name = "modify_name")
    private Timestamp modifyName;

    @Column(name = "create_time")
    private Timestamp createTime;


}
