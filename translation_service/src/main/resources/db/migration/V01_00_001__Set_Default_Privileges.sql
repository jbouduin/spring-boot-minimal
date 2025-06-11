/*
 * Set default privilege granting to minimal_svc
 * for future objects created by minimal_flyway
 */
ALTER DEFAULT PRIVILEGES IN SCHEMA translation_service
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO minimal_svc;

ALTER DEFAULT PRIVILEGES IN SCHEMA translation_service
GRANT SELECT, USAGE, UPDATE ON SEQUENCES TO minimal_svc;

ALTER DEFAULT PRIVILEGES IN SCHEMA translation_service
GRANT EXECUTE ON FUNCTIONS TO minimal_svc;