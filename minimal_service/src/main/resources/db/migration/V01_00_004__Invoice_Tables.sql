CREATE TABLE
  invoice (
    id SERIAL PRIMARY KEY,
    invoice_number VARCHAR(255) NOT NULL,
    customer_name VARCHAR(255) NOT NULL,    
    CONSTRAINT invoice_invoice_number_uk UNIQUE (invoice_number)
  );


CREATE TABLE
  invoice_line (
    id SERIAL PRIMARY KEY,
    invoice_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    quantity NUMERIC NOT NULL,    
    CONSTRAINT invoice_line_invoice_id_fk FOREIGN KEY (invoice_id) REFERENCES invoice (id) ON DELETE CASCADE,
    CONSTRAINT invoice_line_item_id_fk FOREIGN KEY (item_id) REFERENCES item (id) ON DELETE CASCADE
  );