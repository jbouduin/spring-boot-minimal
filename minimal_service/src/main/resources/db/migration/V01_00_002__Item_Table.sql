CREATE TABLE
  item (
    id SERIAL PRIMARY KEY,
    code VARCHAR(255) NOT NULL,    
    price NUMERIC NOT NULL,
    CONSTRAINT item_code_uk UNIQUE (code)
  );