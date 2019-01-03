package com.security.demo.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_permission_visit")
public class UserPermissionVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pv_id")
    private Integer pvId;

    @Column(name = "url")
    private String url;

    @Column(name = "dispaly_name")
    private String dispalyName;

    @Column(name = "menu_key")
    private String menuKey;

    @Column(name = "modify_time")
    private Timestamp modifyTime;

    @Column(name = "create_time")
    private Timestamp createTime;


}
