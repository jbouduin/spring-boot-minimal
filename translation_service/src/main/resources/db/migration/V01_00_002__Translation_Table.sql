CREATE TYPE language_code AS ENUM('DE', 'EN', 'NL', 'FR');

CREATE TABLE
    translation (
        entity_name VARCHAR(255) NOT NULL,
        entity_field VARCHAR(255) NOT NULL,
        entity_id BIGINT,
        language_code language_code NOT NULL,
        translation_text TEXT NOT NULL,
        PRIMARY KEY (entity_name, entity_field, entity_id, language_code)
    );
