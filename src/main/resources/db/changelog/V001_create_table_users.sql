-- liquibase formatted sql

-- changeset Seungwon-Choi:1 runInTransaction:false

CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    email    VARCHAR(20)                       NOT NULL UNIQUE,
    name     VARCHAR(20)                       NOT NULL,
    role     VARCHAR(20)                       NOT NULL,
    provider VARCHAR(30)                       NOT NULL,

    constraint chk_role check (role in ('USER', 'ADMIN'))
);