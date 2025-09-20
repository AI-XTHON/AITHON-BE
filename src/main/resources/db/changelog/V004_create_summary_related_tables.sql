-- liquibase formatted sql

-- changeset Seungwon-Choi:4 runInTransaction:false

-- 1. summaries 테이블 생성
CREATE TABLE summaries
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    resource_id    BIGINT                            NOT NULL UNIQUE,
    filename       VARCHAR(255),
    pages          INT,
    model          VARCHAR(255),
    summary5       TEXT,
    further_topics TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '데이터 생성일자',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '데이터 수정일자',

    CONSTRAINT fk_summaries_on_resource FOREIGN KEY (resource_id) REFERENCES resources (id)
);

-- 2. slides 테이블 생성
CREATE TABLE slides
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    summary_id BIGINT                            NOT NULL,
    title      VARCHAR(255),
    one_liner  TEXT,
    pages      TEXT,
    CONSTRAINT fk_slides_on_summary FOREIGN KEY (summary_id) REFERENCES summaries (id)
);

-- 3. glossary_terms 테이블 생성
CREATE TABLE glossary_terms
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    summary_id BIGINT                            NOT NULL,
    term       VARCHAR(255),
    definition TEXT,
    pages      TEXT,
    CONSTRAINT fk_glossary_terms_on_summary FOREIGN KEY (summary_id) REFERENCES summaries (id)
);

-- 4. questions 테이블 생성
CREATE TABLE questions
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    summary_id BIGINT                            NOT NULL,
    type       VARCHAR(50),
    q          TEXT,
    a          TEXT,
    pages      TEXT,
    CONSTRAINT fk_questions_on_summary FOREIGN KEY (summary_id) REFERENCES summaries (id)
);