/* TODO должна автоматически генериться спринг датой. Надо ли мне ее самому создавать?
    (у меня же flyway) */
CREATE TABLE IF NOT EXISTS catalog_AUD
(
    ID  BIGINT NOT NULL,
    CONSTRAINT fk_catalog_AUD__catalog__many_to_one_constraint
        FOREIGN KEY (ID)
            REFERENCES "catalog" (id),
    REV BIGSERIAL PRIMARY KEY,
    REVTYPE SMALLINT NOT NULL,
    name       VARCHAR(255) NOT NULL UNIQUE,

    /* Persist ops author recoding */
    created_by BIGINT DEFAULT 0,
    updated_by BIGINT DEFAULT 0
);