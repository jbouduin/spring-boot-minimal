/*
 * Set default privilege granting to authentication_svc
 * for future objects created by flyway
 */
ALTER DEFAULT PRIVILEGES IN SCHEMA authentication_service
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO authentication_svc;

ALTER DEFAULT PRIVILEGES IN SCHEMA authentication_service
GRANT SELECT, USAGE, UPDATE ON SEQUENCES TO authentication_svc;

ALTER DEFAULT PRIVILEGES IN SCHEMA authentication_service
GRANT EXECUTE ON FUNCTIONS TO authentication_svc;