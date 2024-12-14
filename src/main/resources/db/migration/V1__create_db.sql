CREATE SEQUENCE seq_note_id
    START WITH 1
    INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS seq_users_id START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS users (
     id BIGINT DEFAULT nextval('seq_users_id'),
     username VARCHAR(50) UNIQUE NOT NULL,
     password_hash VARCHAR(255) NOT NULL,
     role varchar(64) NOT NULL default 'ROLE_USER',
     CONSTRAINT pk_users_id PRIMARY KEY (id)
);

CREATE TABLE note
(
    ID   BIGINT DEFAULT nextval('seq_note_id'),
    TITLE VARCHAR(200) NOT NULL CHECK (LENGTH(TITLE) >= 3),
    CONTENT VARCHAR(2000) NOT NULL CHECK (LENGTH(TITLE) >= 3),
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_client_id PRIMARY KEY (ID),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);



