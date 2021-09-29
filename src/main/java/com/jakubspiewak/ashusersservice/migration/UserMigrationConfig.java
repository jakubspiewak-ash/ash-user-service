package com.jakubspiewak.ashusersservice.migration;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class UserMigrationConfig {
  @Bean
  public Flyway flyway(DataSource dataSource) {
    final var flyway =
        Flyway.configure()
            .locations("migration/user")
            .dataSource(dataSource)
            .schemas("ash_users")
            .load();

    flyway.migrate();

    return flyway;
  }
}
