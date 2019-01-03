package com.security.demo.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_role_info")
public class UserRoleInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "modify_time")
    private Timestamp modifyTime;

    @Column(name = "create_time")
    private Timestamp createTime;


}
