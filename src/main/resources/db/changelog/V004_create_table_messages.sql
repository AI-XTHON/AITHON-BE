-- liquibase formatted sql

-- changeset Seungwon-Choi:4 runInTransaction:false

CREATE TABLE IF NOT EXISTS messages
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY   NOT NULL,
    study_session_id BIGINT                              NOT NULL,
    sender_type      VARCHAR(20)                         NOT NULL,
    content          TEXT                                NOT NULL,
    file_path         TEXT                                NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '데이터 생성일자',
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '데이터 수정일자',

    constraint fk_messages_to_study_sessions foreign key (study_session_id) references study_sessions (id),

    constraint chk_sender_type check (sender_type in ('USER', 'AI'))
);
