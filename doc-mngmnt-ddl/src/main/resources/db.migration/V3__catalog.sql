CREATE TABLE IF NOT EXISTS "catalog"
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL UNIQUE,

    /* Persist ops author recoding */
    created_by BIGINT DEFAULT 0,
    updated_by BIGINT DEFAULT 0
);

/* TODO бан? */
CREATE TABLE IF NOT EXISTS "parent_child_catalog_tree"
(
    parent_id BIGINT NOT NULL,
    CONSTRAINT fk_parent_child_catalog_tree__parent__many_to_many_constraint
        FOREIGN KEY (parent_id)
            REFERENCES "catalog" (id),
    child_id  BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_parent_child_catalog_tree__child__many_to_many_constraint
        FOREIGN KEY (child_id)
            REFERENCES "catalog" (id)
);