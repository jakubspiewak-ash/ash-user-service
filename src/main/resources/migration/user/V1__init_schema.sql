-- RID OF THIS AND USE IN HIBERNATE
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE user_mail_configuration_table
(
    id           uuid    NOT NULL,
    mail_address varchar NOT NULL,
    host         varchar NOT NULL,
    password     varchar NOT NULL,
    port         integer NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_table
(
    id                    uuid    NOT NULL,
    login                 varchar NOT NULL,
    password              varchar NOT NULL,
    mail_configuration_id uuid,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_mail_configuration
        FOREIGN KEY (mail_configuration_id) REFERENCES user_mail_configuration_table (id)
);
