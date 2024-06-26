CREATE TABLE person.merchants
(
    id            UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    creator_id    UUID REFERENCES person.users (id),
    created_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    merchant_name VARCHAR(32),
    email         VARCHAR(32),
    phone_number  VARCHAR(32),
    verified_at   TIMESTAMP,
    status        VARCHAR(8),
    filled        BOOLEAN          default false
);