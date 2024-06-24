CREATE TABLE person.user_actions_history
(
    id             UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    status entity_status,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id     UUID REFERENCES person.users (id),
    reason         VARCHAR(255) DEFAULT 'SYSTEM',
    changed_values jsonb
);