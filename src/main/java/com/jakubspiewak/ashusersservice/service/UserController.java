package com.jakubspiewak.ashusersservice.service;

import com.jakubspiewak.ashapimodellib.model.user.ApiUserCreateRequest;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserGetMailConfigurationResponse;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserMailConfigurationRequest;
import com.jakubspiewak.ashapimodellib.model.user.ApiUserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody ApiUserCreateRequest request) {
        userService.add(request);

        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/mail/configuration")
    public ResponseEntity<List<ApiUserGetMailConfigurationResponse>> getUserWithMailConfiguration() {
        final var users = userService.getAllWithConfiguredMail();

        return ResponseEntity.status(OK).body(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ApiUserUpdateRequest request) {
        userService.update(id, request);

        return ResponseEntity.status(OK).build();
    }

    @PutMapping("/{id}/mail/configuration")
    public ResponseEntity<?> updateMailConfiguration(@PathVariable UUID id, @RequestBody ApiUserMailConfigurationRequest request) {
        userService.updateMailConfiguration(id, request);

        return ResponseEntity.status(OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        userService.delete(id);

        return ResponseEntity.status(OK).build();
    }
}
