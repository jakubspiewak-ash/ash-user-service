package com.jakubspiewak.ashusersservice.service;

import com.jakubspiewak.ashapimodellib.model.user.ApiUserCreateRequest;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserGetMailConfigurationResponse;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserMailConfigurationRequest;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserUpdateRequest;

import java.util.List;
import java.util.UUID;

interface UserService {

    void add(ApiUserCreateRequest request);

    void updateMailConfiguration(UUID id, ApiUserMailConfigurationRequest request);

    List<ApiUserGetMailConfigurationResponse> getAllWithConfiguredMail();

    void update(UUID id, ApiUserUpdateRequest request);

    void delete(UUID id);

}
