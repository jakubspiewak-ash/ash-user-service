package com.jakubspiewak.ashusersservice.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMailConfigurationEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String email;

    private String password;

    private String host;

    private Integer port;
}
