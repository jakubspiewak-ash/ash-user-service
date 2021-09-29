package com.jakubspiewak.ashusersservice.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "user_table")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private UUID id;

  @Column(name = "login")
  private String login;

  @Column(name = "password")
  private String password;

  @OneToOne(orphanRemoval = true, cascade = ALL, fetch = LAZY)
  @JoinColumn(name = "mail_configuration_id", referencedColumnName = "id")
  private UserMailConfigurationEntity mailConfiguration;
}
