CREATE TABLE person.addresses
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP ,
    country_id INTEGER REFERENCES person.countries (id),
    address    VARCHAR(128),
    zip_code   VARCHAR(32),
    city       VARCHAR(32),
    state      VARCHAR(32),
    status VARCHAR(8)
);