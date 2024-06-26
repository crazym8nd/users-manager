CREATE TABLE person.merchant_members
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id     UUID UNIQUE REFERENCES person.users (id),
    merchant_id UUID REFERENCES person.merchants (id),
    member_role VARCHAR(32),
    status VARCHAR(8),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);