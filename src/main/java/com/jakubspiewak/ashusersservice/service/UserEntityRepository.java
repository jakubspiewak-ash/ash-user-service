package com.jakubspiewak.ashusersservice.service;

import com.jakubspiewak.ashusersservice.service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {
    List<UserEntity> findAllByMailConfigurationExists();
}
