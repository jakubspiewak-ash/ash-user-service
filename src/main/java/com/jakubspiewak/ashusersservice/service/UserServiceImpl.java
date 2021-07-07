package com.jakubspiewak.ashusersservice.service;

import com.jakubspiewak.ashapimodellib.model.mail.MailConfiguration;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserCreateRequest;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserGetMailConfigurationResponse;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserMailConfigurationRequest;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserUpdateRequest;
import com.jakubspiewak.ashusersservice.service.entity.UserEntity;
import com.jakubspiewak.ashusersservice.service.entity.UserMailConfigurationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

// TODO: use mapstruct later

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public void add(ApiUserCreateRequest request) {
        final var entity = UserEntity.builder()
                .login(request.getLogin())
                .password(request.getPassword())
                .build();

        userEntityRepository.save(entity);
    }

    @Override
    public List<ApiUserGetMailConfigurationResponse> getAllWithConfiguredMail() {
        final var userEntities = userEntityRepository.findAllByMailConfigurationIsNotNull();
        return userEntities.stream()
                .map(this::mapFromUserEntityToResponse)
                .collect(toList());
    }

    @Override
    public void update(UUID id, ApiUserUpdateRequest request) {
        final var entity = userEntityRepository.findById(id).orElseThrow();

        entity.setPassword(request.getPassword());

        userEntityRepository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        userEntityRepository.deleteById(id);
    }

    @Override
    public void updateMailConfiguration(UUID id, ApiUserMailConfigurationRequest request) {
        final var entity = userEntityRepository.findById(id).orElseThrow();
        final var requestConfiguration = request.getConfiguration();

        final var entityConfiguration = UserMailConfigurationEntity.builder()
                .host(requestConfiguration.getHost())
                .port(requestConfiguration.getPort())
                .email(requestConfiguration.getMailAddress())
                .password(requestConfiguration.getPassword())
                .build();

        entity.setMailConfiguration(entityConfiguration);

        userEntityRepository.save(entity);
    }

    private ApiUserGetMailConfigurationResponse mapFromUserEntityToResponse(UserEntity userEntity) {
        final var entityMailConfiguration = userEntity.getMailConfiguration();
        final var responseMailConfiguration = MailConfiguration.builder()
                .mailAddress(entityMailConfiguration.getEmail())
                .password(entityMailConfiguration.getPassword())
                .port(entityMailConfiguration.getPort())
                .host(entityMailConfiguration.getHost())
                .build();

        return ApiUserGetMailConfigurationResponse.builder()
                .userId(userEntity.getId())
                .mailConfiguration(responseMailConfiguration)
                .build();
    }
}
