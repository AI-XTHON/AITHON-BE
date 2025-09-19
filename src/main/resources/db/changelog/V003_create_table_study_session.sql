-- liquibase formatted sql

-- changeset Seungwon-Choi:3 runInTransaction:false

CREATE TABLE IF NOT EXISTS study_sessions
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY   NOT NULL,
    user_id     BIGINT                              NOT NULL,
    resource_id BIGINT                              NULL,
    title       VARCHAR(100)                        NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '데이터 생성일자',
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '데이터 수정일자',

    constraint fk_study_sessions_to_users foreign key (user_id) references users (id),
    constraint fk_study_sessions_to_resources foreign key (resource_id) references resources (id)
);
