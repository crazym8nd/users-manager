CREATE TABLE person.user_actions_history
(
    id             UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    created        TIMESTAMP NOT NULL,
    profile_id     UUID REFERENCES person.users (id),
    reason         VARCHAR(255) DEFAULT 'SYSTEM',
    changed_values jsonb
);