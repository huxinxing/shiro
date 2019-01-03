package com.security.demo.domain.repository;

import com.security.demo.domain.entity.UserPermissionVisit;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPermissionVisitRepository extends JpaRepository<UserPermissionVisit, Integer> {
}
