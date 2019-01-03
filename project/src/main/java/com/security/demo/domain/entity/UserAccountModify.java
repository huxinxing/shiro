package com.security.demo.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_account_modify")
public class UserAccountModify {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ua_modify_id")
    private Integer uaModifyId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "old_msg")
    private String oldMsg;

    @Column(name = "new_msg")
    private String newMsg;

    @Column(name = "modify_name")
    private Timestamp modifyName;

    @Column(name = "create_time")
    private Timestamp createTime;

}
