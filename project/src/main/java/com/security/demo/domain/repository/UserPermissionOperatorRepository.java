package com.security.demo.domain.repository;

import com.security.demo.domain.entity.UserPermissionOperator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPermissionOperatorRepository extends JpaRepository<UserPermissionOperator,Integer> {
}
