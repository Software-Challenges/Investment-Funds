CREATE EXTENSION IF NOT EXISTS pgcrypto;

DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS subscriptions CASCADE;
DROP TABLE IF EXISTS users_roles CASCADE;
DROP TABLE IF EXISTS funds CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(30),
    balance NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    notification_preference VARCHAR(20) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT chk_users_balance_non_negative CHECK (balance >= 0),
    CONSTRAINT chk_users_notification_preference CHECK (notification_preference IN ('EMAIL', 'SMS'))
);

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users_roles (
    fk_user_id UUID NOT NULL,
    fk_role_id BIGINT NOT NULL,

    PRIMARY KEY (fk_user_id, fk_role_id),

    CONSTRAINT fk_users_roles_user FOREIGN KEY (fk_user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_users_roles_role FOREIGN KEY (fk_role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE funds (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    minimum_amount NUMERIC(15,2) NOT NULL,
    category VARCHAR(50) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT chk_funds_minimum_amount_positive CHECK (minimum_amount > 0),
    CONSTRAINT chk_funds_category CHECK (category IN ('FPV', 'FIC'))
);

CREATE TABLE subscriptions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    amount NUMERIC(15,2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    subscribed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cancelled_at TIMESTAMP,
    fk_user_id UUID NOT NULL,
    fk_fund_id UUID NOT NULL,

    CONSTRAINT fk_subscriptions_user FOREIGN KEY (fk_user_id) REFERENCES users(id),
    CONSTRAINT fk_subscriptions_fund FOREIGN KEY (fk_fund_id) REFERENCES funds(id),
    CONSTRAINT chk_subscriptions_amount_positive CHECK (amount > 0),
    CONSTRAINT chk_subscriptions_status CHECK (status IN ('ACTIVE', 'CANCELLED'))
);

CREATE TABLE transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    type VARCHAR(30) NOT NULL,
    amount NUMERIC(15,2) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fk_user_id UUID NOT NULL,
    fk_subscription_id UUID,
    fk_fund_id UUID NOT NULL,

    CONSTRAINT fk_transactions_user FOREIGN KEY (fk_user_id) REFERENCES users(id),
    CONSTRAINT fk_transactions_subscription FOREIGN KEY (fk_subscription_id) REFERENCES subscriptions(id),
    CONSTRAINT fk_transactions_fund FOREIGN KEY (fk_fund_id) REFERENCES funds(id),
    CONSTRAINT chk_transactions_amount_positive CHECK (amount > 0),
    CONSTRAINT chk_transactions_type CHECK (type IN ('OPEN', 'CANCEL'))
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_subscriptions_user ON subscriptions(fk_user_id);
CREATE INDEX idx_subscriptions_fund ON subscriptions(fk_fund_id);
CREATE INDEX idx_transactions_user ON transactions(fk_user_id);
CREATE INDEX idx_transactions_fund ON transactions(fk_fund_id);
CREATE INDEX idx_transactions_subscription ON transactions(fk_subscription_id);

INSERT INTO roles (name) VALUES ('ADMIN'), ('CLIENT') ON CONFLICT (name) DO NOTHING;

INSERT INTO funds (code, name, minimum_amount, category, is_active)
VALUES
    ('FPV_EL_CLIENTE_RECAUDADORA', 'FPV_EL_CLIENTE_RECAUDADORA', 75000.00, 'FPV', TRUE),
    ('FPV_EL_CLIENTE_ECOPETROL', 'FPV_EL_CLIENTE_ECOPETROL', 125000.00, 'FPV', TRUE),
    ('DEUDAPRIVADA', 'DEUDAPRIVADA', 50000.00, 'FIC', TRUE),
    ('FDO-ACCIONES', 'FDO-ACCIONES', 250000.00, 'FIC', TRUE),
    ('FPV_EL_CLIENTE_DINAMICA', 'FPV_EL_CLIENTE_DINAMICA', 100000.00, 'FPV', TRUE)
    ON CONFLICT (code) DO NOTHING;

INSERT INTO
    users ( id, full_name, email, password, phone_number, balance, notification_preference, is_active, created_at, updated_at)
VALUES
    (
        gen_random_uuid(),
        'Admin User',
        'juansuarezgaviria25@gmail.com',
        '$2y$10$gE8H149Pzo1w0ahwzJC2pOWWNkmPecrJzPgVixcrjlbp.EEFGU5By',
        '+573012863980',
        1000000.00,
        'EMAIL',
        TRUE,
        CURRENT_TIMESTAMP,
        NULL
    ),
    (
        gen_random_uuid(),
        'Client User',
        'stardesign784@gmail.com',
        '$2y$10$Rzy4LD6zBpVA/oQHy0JC7uROZKwrwRPO5zHqEn5RXyhkAT98Ov7.u',
        '+573012863980',
        500000.00,
        'SMS',
        TRUE,
        CURRENT_TIMESTAMP,
        NULL
    )
    ON CONFLICT (email) DO NOTHING;

INSERT INTO users_roles (fk_user_id, fk_role_id)
SELECT u.id, r.id FROM users u JOIN roles r ON r.name = 'ADMIN'
WHERE u.email = 'juansuarezgaviria25@gmail.com' ON CONFLICT DO NOTHING;

INSERT INTO users_roles (fk_user_id, fk_role_id)
SELECT u.id, r.id FROM users u JOIN roles r ON r.name = 'CLIENT'
WHERE u.email = 'stardesign784@gmail.com' ON CONFLICT DO NOTHING;
