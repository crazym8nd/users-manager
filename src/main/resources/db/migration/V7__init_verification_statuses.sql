CREATE TABLE person.verification_statuses
(
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    status              VARCHAR(8),
    created_at          TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    user_id             UUID REFERENCES person.users (id),
    user_type           VARCHAR(32),
    details             VARCHAR(255),
    verification_status VARCHAR(32)
);