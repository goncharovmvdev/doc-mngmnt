/* USER */
CREATE TABLE IF NOT EXISTS "user"
(
    id                      BIGSERIAL PRIMARY KEY,

    /* UserDetails */
    password                VARCHAR(255)             NOT NULL,
    username                VARCHAR(255)             NOT NULL UNIQUE,
    account_non_expired     BOOLEAN                  NOT NULL DEFAULT TRUE,
    account_non_locked      BOOLEAN                  NOT NULL DEFAULT TRUE,
    credentials_non_expired BOOLEAN                  NOT NULL DEFAULT TRUE,
    enabled                 BOOLEAN                  NOT NULL DEFAULT TRUE,

    /* доп. данные о пользователе (ну, например, бует полезная функция восстаноления пароля по email) */
    email                   VARCHAR(255)             NOT NULL UNIQUE,
    phone_number            VARCHAR(255)                      DEFAULT NULL,

    /* Persist ops author recoding */
    created_by              BIGINT                            DEFAULT NULL,
    created                 TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_by              BIGINT                            DEFAULT NULL,
    updated                 TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

/* ROLE */
CREATE TABLE IF NOT EXISTS "role"
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255)             NOT NULL UNIQUE,

    /* Persist ops author recoding */
    created_by BIGINT                            DEFAULT NULL,
    created    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_by BIGINT                            DEFAULT NULL,
    updated    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS user_role
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

/* CATALOG */
CREATE TABLE IF NOT EXISTS catalog
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255)             NOT NULL UNIQUE,

    /* Parent to current catalog */
    parent_id  BIGINT                            DEFAULT NULL,
    CONSTRAINT parent_catalog
        FOREIGN KEY (parent_id)
            REFERENCES catalog (id),

    /* Persist ops author recoding */
    created_by BIGINT                            DEFAULT NULL,
    created    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_by BIGINT                            DEFAULT NULL,
    updated    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

/* DOCUMENT TYPE */
CREATE TABLE IF NOT EXISTS document_type
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255)             NOT NULL UNIQUE,

    /* Persist ops author recoding */
    created_by BIGINT                            DEFAULT NULL,
    created    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_by BIGINT                            DEFAULT NULL,
    updated    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

/* DOCUMENT */
CREATE TABLE IF NOT EXISTS document
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255)             NOT NULL,
    description VARCHAR(255)             NOT NULL,
    importance  VARCHAR(32)                       DEFAULT NULL,

    /* Catalog, containing current document */
    catalog_id  BIGINT,
    CONSTRAINT fk_document__catalog__many_to_one_constraint
        FOREIGN KEY (catalog_id)
            REFERENCES catalog (id),

    /* Persist ops author recoding */
    created_by  BIGINT                            DEFAULT NULL,
    created     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_by  BIGINT                            DEFAULT NULL,
    updated     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS document_document_type
(
    document_id      BIGINT NOT NULL,
    CONSTRAINT fk_document_document_type__document__many_to_many_constraint
        FOREIGN KEY (document_id)
            REFERENCES document (id),
    document_type_id BIGINT NOT NULL,
    CONSTRAINT fk_document_document_type__document_type__many_to_many_constraint
        FOREIGN KEY (document_type_id)
            REFERENCES document_type (id)
);

CREATE TABLE IF NOT EXISTS user_document
(
    user_id     BIGINT NOT NULL,
    CONSTRAINT fk_user_document__user__many_to_many_constraint
        FOREIGN KEY (user_id)
            REFERENCES "user" (id),
    document_id BIGINT NOT NULL,
    CONSTRAINT fk_user_document__document__many_to_many_constraint
        FOREIGN KEY (document_id)
            REFERENCES document (id)
);

/* VERSIONING OF DOCUMENT */
CREATE TABLE IF NOT EXISTS document_version
(
    id          BIGSERIAL PRIMARY KEY,
    document_id BIGINT       NOT NULL,
    CONSTRAINT fk_document_version__document__many_to_one_constraint
        FOREIGN KEY (document_id)
            REFERENCES document (id),

    /* document fields */
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    importance  VARCHAR(32)  NOT NULL,

    /* Catalog, containing current document */
    catalog_id  BIGINT,
    CONSTRAINT fk_document_version__catalog__many_to_one_constraint
        FOREIGN KEY (catalog_id)
            REFERENCES catalog (id)
);

CREATE TABLE IF NOT EXISTS document_version_document_type
(
    document_id      BIGINT NOT NULL,
    CONSTRAINT fk_document_version_document_type__document_version__many_to_many_constraint
        FOREIGN KEY (document_id)
            REFERENCES document (id),
    document_type_id BIGINT NOT NULL,
    CONSTRAINT fk_document_version_document_type__document_type__many_to_many_constraint
        FOREIGN KEY (document_type_id)
            REFERENCES document_type (id)
);

CREATE TABLE IF NOT EXISTS user_document_version
(
    user_id             BIGINT NOT NULL,
    CONSTRAINT fk_user_document_version__user__many_to_many_constraint
        FOREIGN KEY (user_id)
            REFERENCES "user" (id),
    document_version_id BIGINT NOT NULL,
    CONSTRAINT fk_user_document_version__document_version__many_to_many_constraint
        FOREIGN KEY (document_version_id)
            REFERENCES document (id)
);

/* FILE */
CREATE TABLE IF NOT EXISTS file
(
    id          BIGSERIAL PRIMARY KEY,
    path        VARCHAR(255)             NOT NULL,
    document_id BIGINT                   NOT NULL,

    /* Document, containing current file */
    CONSTRAINT fk_file__document__many_to_one_constraint
        FOREIGN KEY (document_id)
            REFERENCES document (id),

    /* Persist ops author recoding */
    created_by  BIGINT                            DEFAULT NULL,
    created     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_by  BIGINT                            DEFAULT NULL,
    updated     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

/* RESET PASSWORD TOKEN */
CREATE TABLE IF NOT EXISTS reset_password_token
(
    id      BIGSERIAL PRIMARY KEY,
    token   VARCHAR(255)             NOT NULL,
    expiry  TIMESTAMP WITH TIME ZONE NOT NULL,

    /* User, corresponding to current reset password token */
    user_id BIGINT                   NOT NULL,
    CONSTRAINT fk_reset_password_token__users__one_to_one_constraint
        FOREIGN KEY (user_id)
            REFERENCES "user" (id)
);