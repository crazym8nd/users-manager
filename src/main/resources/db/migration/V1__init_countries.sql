CREATE TABLE person.countries
(
    id         SERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name       VARCHAR(100),
    alpha2     VARCHAR(2),
    alpha3     VARCHAR(3),
    status     VARCHAR(8)
);
