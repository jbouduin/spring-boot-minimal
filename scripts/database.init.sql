/*
 * Script to be used to initialize the database.
 */

-- Create the schema
CREATE SCHEMA minimal_service;
CREATE SCHEMA authentication_service;

-- Create a single flyway user and grant necessary rights
CREATE USER minimal_flyway WITH PASSWORD 'your_flyway_password';

GRANT USAGE ON SCHEMA minimal_service TO minimal_flyway;
GRANT CONNECT ON DATABASE minimal TO minimal_flyway;
GRANT ALL PRIVILEGES ON SCHEMA minimal_service TO minimal_flyway;
GRANT ALL PRIVILEGES ON SCHEMA authentication_service TO minimal_flyway;

-- Create a single service user and grant necessary rights
CREATE USER minimal_svc WITH PASSWORD 'your_app_password';

GRANT USAGE ON SCHEMA minimal_service TO minimal_svc;
GRANT USAGE ON SCHEMA authentication_service TO minimal_svc;
GRANT CONNECT ON DATABASE minimal TO minimal_svc;

-- End of script
