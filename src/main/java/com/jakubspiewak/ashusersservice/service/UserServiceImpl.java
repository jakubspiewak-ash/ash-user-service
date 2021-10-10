package com.jakubspiewak.ashusersservice.service;

import com.jakubspiewak.ashapimodellib.model.auth.ApiUserCredentials;
import com.jakubspiewak.ashapimodellib.model.mail.MailConfiguration;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserConfigurationRequest;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserConfigurationResponse;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserCreateRequest;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserUpdateRequest;
import com.jakubspiewak.ashusersservice.service.entity.UserEntity;
import com.jakubspiewak.ashusersservice.service.entity.UserMailConfigEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

// TODO: use mapstruct later

@Slf4j
@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

  private final UserEntityRepository userEntityRepository;
  private final TenantService tenantService;

  @Override
  public UUID create(ApiUserCreateRequest request) {
    final var entity =
        UserEntity.builder().login(request.getLogin()).password(request.getPassword()).build();

    final var userId = userEntityRepository.save(entity).getId();

    tenantService.initSchema(userId);

    return userId;
  }

  @Override
  public List<UserEntity> readAll() {
    return userEntityRepository.findAll();
  }

  @Override
  public List<ApiUserConfigurationResponse> getAllWithConfiguredMail() {
    final var userEntities = userEntityRepository.findAllByMailConfigIsNotNull();

    return userEntities.stream().map(this::mapFromUserEntityToResponse).collect(toList());
  }

  @Override
  public void update(UUID id, ApiUserUpdateRequest request) {
    final var entity = userEntityRepository.findById(id).orElseThrow();

    entity.setPassword(request.getPassword());

    userEntityRepository.save(entity);
  }

  // TODO: delete database schema as well
  @Override
  public void delete(UUID id) {
    tenantService.deleteSchema(id);
    userEntityRepository.deleteById(id);
  }

  @Override
  public UUID getIdByCredentials(ApiUserCredentials credentials) {
    return userEntityRepository
        .findByLoginAndPassword(credentials.getLogin(), credentials.getPassword())
        .map(UserEntity::getId)
        .orElseThrow();
  }

  @Override
  public void updateMailConfiguration(UUID id, ApiUserConfigurationRequest request) {
    final var entity = userEntityRepository.findById(id).orElseThrow();

    final var mailConfiguration = request.getMail();

    final var entityConfiguration =
        UserMailConfigEntity.builder()
            .host(mailConfiguration.getHost())
            .port(mailConfiguration.getPort())
            .mailAddress(mailConfiguration.getAddress())
            .password(mailConfiguration.getPassword())
            .build();

    entity.setMailConfig(entityConfiguration);

    userEntityRepository.save(entity);
  }

  private ApiUserConfigurationResponse mapFromUserEntityToResponse(UserEntity userEntity) {
    final var entityMailConfiguration = userEntity.getMailConfig();
    final var responseMailConfiguration =
        MailConfiguration.builder()
            .address(entityMailConfiguration.getMailAddress())
            .password(entityMailConfiguration.getPassword())
            .port(entityMailConfiguration.getPort())
            .host(entityMailConfiguration.getHost())
            .build();

    return ApiUserConfigurationResponse.builder()
        .userId(userEntity.getId())
        .mail(responseMailConfiguration)
        .build();
  }
}
