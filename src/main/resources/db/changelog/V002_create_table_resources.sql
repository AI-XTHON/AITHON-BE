-- liquibase formatted sql

-- changeset Seungwon-Choi:2 runInTransaction:false

CREATE TABLE IF NOT EXISTS resources
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY   NOT NULL,
    user_id    BIGINT                              NOT NULL,
    file_name  VARCHAR(255)                        NOT NULL,
    file_path  TEXT                                NOT NULL,
    file_type  VARCHAR(50)                         NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '데이터 생성일자',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '데이터 수정일자',

    constraint fk_user_id foreign key (user_id) references users (id)
);
