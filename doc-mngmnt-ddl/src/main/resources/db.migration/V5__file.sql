CREATE TABLE IF NOT EXISTS "file"
(
    id          BIGSERIAL PRIMARY KEY,
    path        VARCHAR(255) NOT NULL,
    document_id BIGINT       NOT NULL,

    /* Document, containing current file */
    CONSTRAINT fk_file__document__many_to_one_constraint
        FOREIGN KEY (document_id)
            REFERENCES "document" (id),

    /* Persist ops author recoding */
    created_by  BIGINT DEFAULT 0,
    updated_by  BIGINT DEFAULT 0
);