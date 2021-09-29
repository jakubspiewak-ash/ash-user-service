package com.jakubspiewak.ashusersservice.service;

import com.jakubspiewak.ashapimodellib.model.auth.ApiUserCredentials;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserConfigurationRequest;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserConfigurationResponse;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserCreateRequest;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

// TODO: when it isn't in separate schema improve security
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  // TODO: remove "/register" after gateway security improvement
  @PostMapping("/register")
  public ResponseEntity<UUID> add(@RequestBody ApiUserCreateRequest request) {
    final var id = userService.create(request);

    return ResponseEntity.status(CREATED).body(id);
  }

  @PostMapping("/id")
  public ResponseEntity<UUID> findByCredentials(@RequestBody ApiUserCredentials credentials) {
    final var id = userService.getIdByCredentials(credentials);
    return ResponseEntity.status(OK).body(id);
  }

  @GetMapping("/configuration")
  public ResponseEntity<List<ApiUserConfigurationResponse>> getUserWithMailConfiguration() {
    final var users = userService.getAllWithConfiguredMail();

    return ResponseEntity.status(OK).body(users);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
      @PathVariable UUID id, @RequestBody ApiUserUpdateRequest request) {
    userService.update(id, request);

    return ResponseEntity.status(OK).build();
  }

  //  @PutMapping("/{id}/mail/configuration")
  //  public ResponseEntity<?> updateMailConfiguration(
  //      @PathVariable UUID id, @RequestBody ApiUserMailConfigurationRequest request) {
  //    userService.updateMailConfiguration(id, request);
  //
  //    return ResponseEntity.status(OK).build();
  //  }

  @PutMapping("/configuration")
  public ResponseEntity<?> updateMailConfiguration(
      @RequestBody ApiUserConfigurationRequest request) {
    userService.updateMailConfiguration(null, request);

    return ResponseEntity.status(OK).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable UUID id) {
    userService.delete(id);

    return ResponseEntity.status(OK).build();
  }
}
