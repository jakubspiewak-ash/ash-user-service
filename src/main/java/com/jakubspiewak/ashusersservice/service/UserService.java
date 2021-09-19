package com.jakubspiewak.ashusersservice.service;

import com.jakubspiewak.ashapimodellib.model.auth.UserCredentials;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserConfigurationRequest;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserConfigurationResponse;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserCreateRequest;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserUpdateRequest;
import com.jakubspiewak.ashusersservice.service.entity.UserEntity;

import java.util.List;
import java.util.UUID;

interface UserService {

    UUID create(ApiUserCreateRequest request);

    List<UserEntity> readAll();

    void updateMailConfiguration(UUID id, ApiUserConfigurationRequest request);

    List<ApiUserConfigurationResponse> getAllWithConfiguredMail();

    void update(UUID id, ApiUserUpdateRequest request);

    void delete(UUID id);

    UUID getIdByCredentials(UserCredentials credentials);
}
