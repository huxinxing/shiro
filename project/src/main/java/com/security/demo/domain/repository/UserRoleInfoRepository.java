package com.security.demo.domain.repository;

import com.security.demo.domain.entity.UserRoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleInfoRepository extends JpaRepository<UserRoleInfo, Integer> {
}
