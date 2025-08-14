CREATE TABLE IF NOT EXISTS carrier (
    id BIGSERIAL PRIMARY KEY,
    carrier_name VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS route (
    id BIGSERIAL PRIMARY KEY,
    origin VARCHAR(255) NOT NULL,
    destination VARCHAR(255) NOT NULL,
    carrier_id BIGINT NOT NULL REFERENCES carrier(id),
    duration_minutes INT NOT NULL CHECK (duration_minutes > 0)
);

CREATE TABLE IF NOT EXISTS ticket (
    id BIGSERIAL PRIMARY KEY,
    route_id BIGINT NOT NULL REFERENCES route(id),
    departure_ts TIMESTAMPTZ NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    price BIGINT NOT NULL CHECK (price >= 0),
    is_sold BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE(route_id, departure_ts, seat_number)
);

CREATE TABLE IF NOT EXISTS app_user (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS ticket_purchase (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES app_user(id),
    ticket_id BIGINT NOT NULL REFERENCES ticket(id),
    price BIGINT NOT NULL CHECK (price >= 0),
    purchase_ts TIMESTAMPTZ NOT NULL DEFAULT now(),
    UNIQUE (ticket_id)
);