package com.jakubspiewak.ashusersservice.service;

import com.jakubspiewak.ashapimodellib.model.auth.ApiUserCredentials;
import com.jakubspiewak.ashapimodellib.model.mail.MailConfiguration;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserConfigurationRequest;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserConfigurationResponse;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserCreateRequest;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserUpdateRequest;
import com.jakubspiewak.ashusersservice.service.entity.UserEntity;
import com.jakubspiewak.ashusersservice.service.entity.UserMailConfigurationEntity;
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

  @Override
  public UUID create(ApiUserCreateRequest request) {
    // TODO: change that shit later

    //    final var entity =
    //
    // UserEntity.builder().login(request.getLogin()).password(request.getPassword()).build();

    final var entity =
        userEntityRepository.findAll().stream()
            .findFirst()
            .orElseGet(() -> UserEntity.builder().build());

    entity.setLogin(request.getLogin());
    entity.setPassword(request.getPassword());
    return userEntityRepository.save(entity).getId();
  }

  @Override
  public List<UserEntity> readAll() {
    return userEntityRepository.findAll();
  }

  @Override
  public List<ApiUserConfigurationResponse> getAllWithConfiguredMail() {
    final var userEntities = userEntityRepository.findAllByMailConfigurationIsNotNull();

    return userEntities.stream().map(this::mapFromUserEntityToResponse).collect(toList());
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
  public UUID getIdByCredentials(ApiUserCredentials credentials) {
    return userEntityRepository
        .findByLoginAndPassword(credentials.getUsername(), credentials.getPassword())
        .map(UserEntity::getId)
        .orElseThrow();
  }

  @Override
  public void updateMailConfiguration(UUID id, ApiUserConfigurationRequest request) {
    //    final var entity = userEntityRepository.findById(id).orElseThrow();
    final var entity = userEntityRepository.findAll().stream().findAny().orElseThrow();
    final var mailConfiguration = request.getMail();

    final var entityConfiguration =
        UserMailConfigurationEntity.builder()
            .host(mailConfiguration.getHost())
            .port(mailConfiguration.getPort())
            .address(mailConfiguration.getAddress())
            .password(mailConfiguration.getPassword())
            .build();

    entity.setMailConfiguration(entityConfiguration);

    userEntityRepository.save(entity);
  }

  private ApiUserConfigurationResponse mapFromUserEntityToResponse(UserEntity userEntity) {
    final var entityMailConfiguration = userEntity.getMailConfiguration();
    final var responseMailConfiguration =
        MailConfiguration.builder()
            .address(entityMailConfiguration.getAddress())
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
