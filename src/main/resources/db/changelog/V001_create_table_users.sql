-- liquibase formatted sql

-- changeset Seungwon-Choi:1 runInTransaction:false

CREATE TABLE IF NOT EXISTS users
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY   NOT NULL,
    email         VARCHAR(255)                        NOT NULL UNIQUE,
    name          VARCHAR(20)                         NOT NULL,
    role          VARCHAR(20)                         NOT NULL,
    provider      VARCHAR(30)                         NOT NULL,
    refresh_token TEXT                                NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '데이터 생성일자',
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '데이터 수정일자',

    constraint chk_role check (role in ('USER', 'ADMIN'))
);