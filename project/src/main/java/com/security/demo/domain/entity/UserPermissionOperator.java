package com.security.demo.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_permission_visit")
public class UserPermissionOperator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "po_id")
    private Integer poId;

    @Column(name = "dispaly_name")
    private String dispalyName;

    @Column(name = "button_key")
    private String buttonKey;

    @Column(name = "modify_time")
    private Timestamp modifyTime;

    @Column(name = "create_time")
    private Timestamp createTime;


}
