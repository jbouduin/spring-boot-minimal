/*
 * Set default privilege granting to translation_svc
 * for future objects created by flyway
 */
ALTER DEFAULT PRIVILEGES IN SCHEMA translation_service
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO translation_svc;

ALTER DEFAULT PRIVILEGES IN SCHEMA translation_service
GRANT SELECT, USAGE, UPDATE ON SEQUENCES TO translation_svc;

ALTER DEFAULT PRIVILEGES IN SCHEMA translation_service
GRANT EXECUTE ON FUNCTIONS TO translation_svc;
