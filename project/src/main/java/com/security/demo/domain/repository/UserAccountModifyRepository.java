package com.security.demo.domain.repository;

import com.security.demo.domain.entity.UserAccountModify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountModifyRepository extends JpaRepository<UserAccountModify,Integer> {
}
