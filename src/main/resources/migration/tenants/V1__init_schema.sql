CREATE TABLE file_table
(
    id    uuid    NOT NULL,
    name  varchar NOT NULL,
    bytes bytea   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE expense_mail_config_table
(
    id                 uuid    NOT NULL,
    mail_address       varchar NOT NULL,
    attachment_pattern varchar,
    PRIMARY KEY (id)
);

CREATE TABLE expense_table
(
    id             uuid    NOT NULL,
    name           varchar NOT NULL,
    amount         numeric NOT NULL,
    mail_config_id uuid,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_mail_configuration FOREIGN KEY (mail_config_id) REFERENCES expense_mail_config_table (id)
);

