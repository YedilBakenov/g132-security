package com.example.g132_security.repository;

import com.example.g132_security.model.Permission;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    @Query("SELECT p from Permission p WHERE p.role='ROLE_USER'")
    Permission getStandartPermission();
}
