package com.jakubspiewak.ashusersservice.service.entity;

import com.jakubspiewak.ashusersservice.service.entity.UserMailConfigurationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String login;

    private String password;

    @OneToOne(orphanRemoval = true)
    private UserMailConfigurationEntity mailConfiguration;
}
