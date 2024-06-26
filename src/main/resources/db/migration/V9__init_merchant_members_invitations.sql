CREATE TABLE person.merchant_members_invitations
(
    id             UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    status      VARCHAR(8),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at  TIMESTAMP NOT NULL,
    merchant_id UUID REFERENCES person.merchants (id),
    first_name  VARCHAR(32),
    last_name   VARCHAR(32),
    email       VARCHAR(32)
);