CREATE TABLE IF NOT EXISTS "user"
(
    id                      BIGSERIAL PRIMARY KEY,

    /* UserDetails */
    password                VARCHAR(255) NOT NULL,
    username                VARCHAR(255) NOT NULL UNIQUE,
    account_non_expired     BOOLEAN      NOT NULL DEFAULT TRUE,
    account_non_locked      BOOLEAN      NOT NULL DEFAULT TRUE,
    credentials_non_expired BOOLEAN      NOT NULL DEFAULT TRUE,
    enabled                 BOOLEAN      NOT NULL DEFAULT TRUE,

    /* 2 phase authentication + доп. данные о пользователе
     *  (ну например бует полезная функция восстаноления пароля через email) */
    email                   VARCHAR(255) NOT NULL,
    phone_number            VARCHAR(255)          DEFAULT NULL,

    /* Persist ops author recoding */
    created_by              BIGINT                DEFAULT 0,
    updated_by              BIGINT                DEFAULT 0
);