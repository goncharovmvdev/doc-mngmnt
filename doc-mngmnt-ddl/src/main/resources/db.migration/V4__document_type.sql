CREATE TABLE IF NOT EXISTS "document_type"
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL UNIQUE,

    /* Persist ops author recoding */
    created_by BIGINT DEFAULT 0,
    updated_by BIGINT DEFAULT 0
);