CREATE TABLE IF NOT EXISTS "role"
(
    id         BIGSERIAL PRIMARY KEY,
    role       VARCHAR(255) NOT NULL UNIQUE,

    /* Persist ops author recoding */
    created_by BIGINT DEFAULT 0,
    updated_by BIGINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS "user_role"
(
    user_id BIGINT,
    CONSTRAINT fk_user_role__user__many_to_many_constraint
        FOREIGN KEY (user_id)
            REFERENCES "user" (id),
    role_id BIGINT,
    CONSTRAINT fk_user_role__role__many_to_many_constraint
        FOREIGN KEY (role_id)
            REFERENCES "role" (id)
);

/*
CREATE TABLE IF NOT EXISTS two_factor_authorization_token
(
    id      BIGSERIAL PRIMARY KEY,
    token   VARCHAR(255) NOT NULL,
    user_id BIGINT       NOT NULL,
    CONSTRAINT fk_two_factor_authorization_token__user__one_to_one_constraint
        FOREIGN KEY (user_id)
            REFERENCES "user" (id)
);
*/