package com.jakubspiewak.ashusersservice.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "user_mail_configuration_table")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMailConfigurationEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "mail")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "host")
    private String host;

    @Column(name = "port")
    private Integer port;
}
