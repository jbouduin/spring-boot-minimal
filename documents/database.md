# Principles

## Database users
- every service has it's own schema in the database.
- every service _can_ have it's own database user, which can then only access it's own schema.
- the database user cannot execute DDL statements.

## Flyway
- A dedicated flyway user _can_ be defined for every service.
- The flyway user has elevated rights in it's own schema.

## The role of the Databse administrator
- create the schemas for the services
- create the service users and assign them the required privileges to the schema
- create the flyway users and assign them the required privileges to the schema

## Remarks:
- If desired, the system could even be configured to have a separate database for every service, instead of only a database schema.
- Although Flyway technically can create schema's in a database, The flyway users of the services are not granted the right to do so and a database administator has to create them.

# Required setup when creating a new Service that needs the database

## Create required database objects
As a database administrator, execute following script after connecting to the database:
```sql
-- Create the service schema
CREATE SCHEMA <service_schema_name>;

-- Create the flyway user and grant required privileges, if you plan to use flyway
CREATE USER <flyway_user> WITH PASSWORD 'flyway user password';
GRANT CONNECT ON DATABASE <database_name> TO <flyway_user>;
GRANT ALL PRIVILEGES ON SCHEMA <service_schema_name> TO <flyway_user>;

-- Create the authentication_svc user and grant required privileges
CREATE USER <service_user> WITH PASSWORD 'service user password';
GRANT CONNECT ON DATABASE <database_name> TO <service_user>;
GRANT USAGE ON SCHEMA <service_schema_name> TO <service_user>;
```

## Set the required values in application properties
This can be done using .env files, or directly in the application.properties file.
Following properties need to be set in application.properties:

```properties
# *****************************************************************************
# Database
# *****************************************************************************
spring.datasource.url=jdbc:postgresql://localhost:5432/<database_name>
spring.datasource.username=<service_user>
spring.datasource.password='service user password'
spring.jpa.properties.hibernate.default_schema=<service_schema_name>

# *****************************************************************************
# Flyway
# *****************************************************************************
der-e-coach.flyway.datasource.url=jdbc:postgresql://localhost:5432/<database_name>
der-e-coach.flyway.datasource.default_schema=<service_schema_name>
der-e-coach.flyway.datasource.username=<flyway_user>
der-e-coach.flyway.datasource.password='flyway user password'
der-e-coach.flyway.datasource.driver-class-name=org.postgresql.Driver
der-e-coach.flyway.enabled=true
der-e-coach.flyway.validate-on-migrate=true
der-e-coach.flyway.baseline-on-migrate=true
der-e-coach.flyway.baseline-version=0
```

When using the available .env files as describe [here](env_files.md), you have to set the following environment variables:

```properties
# *****************************************************************************
# Database
# *****************************************************************************
DATASOURCE_URL=jdbc:postgresql://localhost:5432/minimal
DATASOURCE_DEFAULT_SCHEMA=<service_schema_name>
DATASOURCE_USERNAME=<service_user>
DATASOURCE_PASSWORD='service user password'

# *****************************************************************************
# Flyway
# *****************************************************************************
FLYWAY_USERNAME=<flyway_user>
FLYWAY_PASSWORD='flyway user password'
FLYWAY_DRIVER=org.postgresql.Driver
FLYWAY_ENABLED=true
FLYWAY_VALIDATE_ON_MIGRATE=true
FLYWAY_BASELINE_ON_MIGRATE=true
FLYWAY_BASELINE_VERSION=0
```

The last four flyway properties in application.properties or the .env file depend on what you want.

## Add a dependency to the shared library in the pom file
```xml
    <dependency>
      <groupId>de.der_e_coach</groupId>
      <artifactId>shared_lib</artifactId>
      <version>0.0.1-SNAPSHOT</version>
    </dependency> 
```
The shared library contains a FlywayConfiguration bean and uses a DelayedFlywayMigration strategy, which allows for injectable migration beans.

If the shared library is used, the following dependency can be ommitted in the pom file of the service
```xml
    <dependency>
      <groupId>org.flywaydb</groupId>
      <artifactId>flyway-core</artifactId>      
    </dependency>
```
However, the following dependency must be added:
```xml
    <dependency>
			<groupId>org.flywaydb</groupId>
			<artifactId>flyway-database-postgresql</artifactId>
		</dependency>
```

## Add the first database migration script
The first database migration script __MUST__ contain at least the following statements.
This will grant the required privileges on future database objects created by flyway.
```sql
/*
 * Set default privilege granting to <service_user>
 * for future objects created by <flyway_user>
 */
ALTER DEFAULT PRIVILEGES IN SCHEMA <service_schema_name>
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO <service_user>;

ALTER DEFAULT PRIVILEGES IN SCHEMA <service_schema_name>
GRANT SELECT, USAGE, UPDATE ON SEQUENCES TO <service_user>;

ALTER DEFAULT PRIVILEGES IN SCHEMA <service_schema_name>
GRANT EXECUTE ON FUNCTIONS TO <service_user>;
```

# Open issue(s)
## Views

Postgres does not allow to set default privileges on views.
```SQL
ALTER DEFAULT PRIVILEGES IN SCHEMA <service_schema_name>
GRANT SELECT ON VIEWS TO <service_user>;
```

A workaround, if required one day, could be to create a function and a trigger that sets the select privilege after creatig a view. 

### The function: 
```SQL
CREATE OR REPLACE FUNCTION grant_view_privileges()
RETURNS event_trigger AS $$
BEGIN
    EXECUTE 'GRANT SELECT ON ALL VIEWS IN SCHEMA translation_service TO translation_svc';
END;
$$ LANGUAGE plpgsql;
```

### The trigger
```SQL
CREATE EVENT TRIGGER grant_view_privileges_trigger
ON ddl_command_end
WHEN TAG IN ('CREATE VIEW')
EXECUTE FUNCTION grant_view_privileges();
```

## Types
Postgres supports
```SQL
ALTER DEFAULT PRIVILEGES IN SCHEMA <service_schema_name>
GRANT USAGE ON TYPES TO <service_user>;
```
Although the translation service has a type defined, and this statement has never been executed, the translation service user works. 
