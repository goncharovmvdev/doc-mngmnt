CREATE TABLE IF NOT EXISTS reset_password_token
(
    id     BIGSERIAL PRIMARY KEY,
    token  VARCHAR(255) NOT NULL,
    expiry TIMESTAMP WITH TIME ZONE NOT NULL,

    /* User, corresponding to current reset password token */
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_reset_password_token__users__one_to_one_constraint
        FOREIGN KEY (user_id)
            REFERENCES "user" (id)
);