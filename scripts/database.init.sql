/*
 * Script to be used to initialize the database for local setup.
 */

-- ----------------------------------------------------------------------------
-- Authentication service
-- ----------------------------------------------------------------------------
-- Create the authentication_service schema
CREATE SCHEMA authentication_service;

-- Create the authentication_flyway user and grant required privileges
CREATE USER authentication_flyway WITH PASSWORD 'authentication_flyway';
GRANT CONNECT ON DATABASE minimal TO authentication_flyway;
GRANT ALL PRIVILEGES ON SCHEMA authentication_service TO authentication_flyway;

-- Create the authentication_svc user and grant required privileges
CREATE USER authentication_svc WITH PASSWORD 'authentication_svc';
GRANT CONNECT ON DATABASE minimal TO authentication_svc;
GRANT USAGE ON SCHEMA authentication_service TO authentication_svc;

-- ----------------------------------------------------------------------------
-- Minimal service
-- ----------------------------------------------------------------------------
-- Create the minimal_service schema
CREATE SCHEMA minimal_service;

-- Create the minimal_flyway user and grant required privileges
CREATE USER minimal_flyway WITH PASSWORD 'minimal_flyway';
GRANT CONNECT ON DATABASE minimal TO minimal_flyway;
GRANT ALL PRIVILEGES ON SCHEMA minimal_service TO minimal_flyway;

-- Create the minimal_svc user and grant required privileges
CREATE USER minimal_svc WITH PASSWORD 'minimal_svc';
GRANT CONNECT ON DATABASE minimal TO minimal_svc;
GRANT USAGE ON SCHEMA minimal_service TO minimal_svc;

-- ----------------------------------------------------------------------------
-- Translation service
-- ----------------------------------------------------------------------------
-- Create the translation_service schema
CREATE SCHEMA translation_service;

-- Create the translation_flyway user and grant required privileges
CREATE USER translation_flyway WITH PASSWORD 'translation_flyway';
GRANT CONNECT ON DATABASE minimal TO translation_flyway;
GRANT ALL PRIVILEGES ON SCHEMA translation_service TO translation_flyway;

-- Create the translation_svc user and grant required privileges
CREATE USER translation_svc WITH PASSWORD 'translation_svc';
GRANT CONNECT ON DATABASE minimal TO translation_svc;
GRANT USAGE ON SCHEMA translation_service TO translation_svc;

-- End of script
