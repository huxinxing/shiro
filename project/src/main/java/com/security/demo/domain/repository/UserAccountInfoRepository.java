package com.security.demo.domain.repository;

import com.security.demo.domain.entity.UserAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountInfoRepository extends JpaRepository<UserAccountInfo,Integer> {

    UserAccountInfo findByDisplayName(String displayName);

}
