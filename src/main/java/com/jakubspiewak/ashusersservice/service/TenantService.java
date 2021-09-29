package com.jakubspiewak.ashusersservice.service;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TenantService {

  private final DataSource dataSource;

  public void initSchema(UUID userId) {
    final var schemaName = getSchemaNameForUser(userId);
    final var flyway =
        Flyway.configure()
            .locations("migration/tenants")
            .dataSource(dataSource)
            .schemas(schemaName)
            .load();
    flyway.migrate();
  }

  public void deleteSchema(UUID userId) {
    final var schemaName = getSchemaNameForUser(userId);

    try (final var connection = dataSource.getConnection()) {
      System.out.println(connection.getSchema());
      connection
          .createStatement()
          .execute(String.format("DROP SCHEMA IF EXISTS \"%s\" CASCADE;", schemaName));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String getSchemaNameForUser(UUID userId) {
    return String.format("user-%s", userId.toString());
  }
}
