CREATE TABLE repo
(
    name      VARCHAR(255) PRIMARY KEY,
    url       VARCHAR(255) NOT NULL,
    repo_type VARCHAR(255) NOT NULL
);

CREATE TABLE artefact
(
    artefact_id VARCHAR(255) PRIMARY KEY
);

CREATE TABLE dependency
(
    parent_id   VARCHAR(255),
    artefact_id VARCHAR(255)
);

CREATE TABLE artefact_file
(
    id       BIGINT PRIMARY KEY,
    artefact VARCHAR(255) NOT NULL,
    filename VARCHAR(255) NOT NULL,
    type     VARCHAR(255) NOT NULL,
    path     VARCHAR(255) NOT NULL
);

CREATE SEQUENCE artefact_file_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE HIBERNATE_SEQUENCE
    START WITH 1
    INCREMENT BY 1;