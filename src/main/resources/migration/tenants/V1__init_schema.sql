CREATE TABLE file_table
(
    id    uuid    NOT NULL,
    name  varchar NOT NULL,
    bytes bytea   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE expense_table
(
    id                 uuid    NOT NULL,
    name               varchar NOT NULL,
    amount             numeric NOT NULL,
    mail_address       varchar,
    attachment_pattern varchar,
    PRIMARY KEY (id)
);