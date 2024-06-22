CREATE TABLE person.verification_statuses
(
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    created             TIMESTAMP NOT NULL,
    updated             TIMESTAMP NOT NULL,
    user_id          UUID REFERENCES person.users (id),
    user_type        VARCHAR(32),
    details             VARCHAR(255),
    verification_status VARCHAR(32)
);