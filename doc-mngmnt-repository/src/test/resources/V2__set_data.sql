/* INIT USERS */
INSERT INTO "user"(password, username, email)
VALUES ('password', 'admin', 'admin@digdes.com');
INSERT INTO "user"(password, username, email)
VALUES ('password', 'tester', 'tester@digdes.com');
INSERT INTO "user"(password, username, email)
VALUES ('password', 'user', 'user@digdes.com');

/* INIT ROLES */
INSERT INTO "role"(name)
VALUES ('ROLE_ADMIN');
INSERT INTO "role"(name)
VALUES ('ROLE_TESTER');
INSERT INTO "role"(name)
VALUES ('ROLE_USER');

/* ASSIGN ROLES TO USERS */
INSERT INTO user_role(user_id, role_id)
VALUES (1, 1);
INSERT INTO user_role(user_id, role_id)
VALUES (1, 2);
INSERT INTO user_role(user_id, role_id)
VALUES (1, 3);
INSERT INTO user_role(user_id, role_id)
VALUES (2, 2);
INSERT INTO user_role(user_id, role_id)
VALUES (2, 3);
INSERT INTO user_role(user_id, role_id)
VALUES (3, 3);

/* ADD DOCUMENTS */
INSERT INTO document(title, description)
VALUES ('document1', 'first test document');
INSERT INTO document(title, description)
VALUES ('document2', 'second test document');
INSERT INTO document(title, description)
VALUES ('document3', 'third test document');

/* ADD DOCUMENT TYPES */
INSERT INTO document_type(name)
VALUES ('letter');
INSERT INTO document_type(name)
VALUES ('fax');
INSERT INTO document_type(name)
VALUES ('poem');

/* ASSIGN DOCUMENTS TO THEIR TYPES */
INSERT INTO document_document_type(DOCUMENT_ID, DOCUMENT_TYPE_ID)
VALUES (1, 1);
INSERT INTO document_document_type(DOCUMENT_ID, DOCUMENT_TYPE_ID)
VALUES (2, 2);
INSERT INTO document_document_type(DOCUMENT_ID, DOCUMENT_TYPE_ID)
VALUES (3, 3);

/* ASSIGN DOCUMENTS TO THEIR (CURRENT) OWNERS */
INSERT INTO user_document(user_id, document_id)
VALUES (1, 1);
INSERT INTO user_document(user_id, document_id)
VALUES (2, 2);
INSERT INTO user_document(user_id, document_id)
VALUES (3, 3);

