CREATE TABLE account (
  id                  BIGSERIAL    NOT NULL PRIMARY KEY,
  email               VARCHAR(255) NOT NULL,
  password            VARCHAR(255) NOT NULL,
  roles               VARCHAR(255) NOT NULL,
  created             TIMESTAMP    NOT NULL,
  last_login          TIMESTAMP    NOT NULL,
  last_password_change TIMESTAMP   DEFAULT NOW(),
  enabled             BOOLEAN      NOT NULL,
  account_expired     BOOLEAN      NOT NULL,
  account_locked      BOOLEAN      NOT NULL,
  credentials_expired BOOLEAN      NOT NULL,
  first_name          VARCHAR(255) NOT NULL,
  last_name           VARCHAR(255) NOT NULL,
  updated             TIMESTAMP DEFAULT NOW(),

  UNIQUE (email)
);
