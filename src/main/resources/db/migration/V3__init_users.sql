CREATE TABLE person.users
(
    id           UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    secret_key   VARCHAR(32),
    created_at   TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP,
    first_name   VARCHAR(50),
    last_name    VARCHAR(50),
    verified_at  TIMESTAMP,
    phone_number VARCHAR(20)                           NOT NULL,
    email        VARCHAR(32)                           NOT NULL,
    status       VARCHAR(8),
    filled       BOOLEAN          DEFAULT false,
    address_id   UUID REFERENCES person.addresses (id) NULL
);