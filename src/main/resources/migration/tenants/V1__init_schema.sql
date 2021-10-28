CREATE TABLE file_table
(
    id    uuid    NOT NULL,
    name  varchar NOT NULL,
    bytes bytea   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE expense_date_range_table
(
    id    uuid NOT NULL,
    start date NOT NULL,
    "end" date,
    PRIMARY KEY (id)
);

CREATE TABLE expense_mail_config_table
(
    id                 uuid    NOT NULL,
    mail_address       varchar NOT NULL,
    attachment_pattern varchar,
    PRIMARY KEY (id)
);

CREATE TABLE expense_amount
(
    id       uuid    NOT NULL,
    net      decimal NOT NULL,
    gross    decimal NOT NULL,
    vat      decimal NOT NULL,
    currency varchar NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE expense_table
(
    id             uuid    NOT NULL,
    name           varchar NOT NULL,
    date_range_id  uuid    NOT NULL,
    amount_id      uuid    NOT NULL,
    is_private     boolean NOT NULL,
    mail_config_id uuid,
    PRIMARY KEY (id),
    CONSTRAINT fk_date_range FOREIGN KEY (date_range_id) REFERENCES expense_date_range_table (id),
    CONSTRAINT fk_amount FOREIGN KEY (amount_id) REFERENCES expense_amount (id),
    CONSTRAINT fk_mail_configuration FOREIGN KEY (mail_config_id) REFERENCES expense_mail_config_table (id)
);

