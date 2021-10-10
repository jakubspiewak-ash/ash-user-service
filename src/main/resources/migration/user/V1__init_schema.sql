CREATE TABLE user_mail_config_table
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
    id             uuid    NOT NULL,
    login          varchar NOT NULL,
    password       varchar NOT NULL,
    mail_config_id uuid,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_mail_config FOREIGN KEY (mail_config_id) REFERENCES user_mail_config_table (id)
);
