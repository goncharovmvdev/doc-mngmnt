CREATE TABLE IF NOT EXISTS "document"
(
    id               BIGSERIAL PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    description      VARCHAR(255) NOT NULL,
    priority         VARCHAR(32)  NOT NULL,

    /* Catalog, containing current document */
    catalog_id       BIGINT,
    CONSTRAINT fk_document__catalog__many_to_one_constraint
        FOREIGN KEY (catalog_id)
            REFERENCES "catalog" (id),

    /* Persist ops author recoding */
    created_by       BIGINT DEFAULT 0,
    updated_by       BIGINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS "document_document_type"
(
    document_id BIGINT NOT NULL,
    CONSTRAINT fk_document_document_type__document_many_to_many_constraint
        FOREIGN KEY (document_id)
            REFERENCES "document" (id),
    document_type_id BIGINT NOT NULL,
    CONSTRAINT fk_document_document_type__document_type_many_to_many_constraint
        FOREIGN KEY (document_type_id)
            REFERENCES "document_type" (id)
);

CREATE TABLE IF NOT EXISTS "user_document"
(
    user_id     BIGINT NOT NULL,
    CONSTRAINT fk_user_document__user__many_to_many_constraint
        FOREIGN KEY (user_id)
            REFERENCES "user" (id),
    document_id BIGINT NOT NULL,
    CONSTRAINT fk_user_document__document__many_to_many_constraint
        FOREIGN KEY (document_id)
            REFERENCES "document" (id)
);