-- create account table
CREATE TABLE
  account (
    id SERIAL PRIMARY KEY,
    account_name VARCHAR(255) NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) DEFAULT NULL,
    last_name VARCHAR(255) DEFAULT NULL,
    email VARCHAR(255) DEFAULT NULL,
    active BOOLEAN DEFAULT TRUE
  );

-- Create unique indexes for case-insensitive columns
CREATE UNIQUE INDEX accounts_account_name_unique ON account (LOWER(account_name));

CREATE UNIQUE INDEX accounts_email_unique ON account (LOWER(email));

-- create role table
CREATE TABLE
  account_role (
    account_id BIGINT NOT NULL,
    account_role VARCHAR(50) NOT NULL,
    PRIMARY KEY (account_id, account_role),
    CONSTRAINT account_role_account_id_fk FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE
  );
