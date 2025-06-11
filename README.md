# der-e-coach.site-service
Spring boot backend project for PoC's.
Originally created to check how much code one can be moved to the shared library, before things start going wrong and the system breaks.

# Local setup (description for Windows)

## Prerequisites
- have docker installed
- have a postgreSQL container running

## Create and Initialize postgress database
- open a windows terminal and run 
  - `docker exec -it <name of container> psql -U postgres -c "CREATE DATABASE minimal;`
  - `cd scripts`
  - `initialize_local_postgres.bat`. It will copy [database.init.sql](scripts/database.init.sql) to the container and execute it. The script performs the following actions:
    - create the schemas `minimal_service` and  `authorization_service` in the `minimal` database.
    - create a single flyway user `minimal_flyway_flyway` with password `your_flyway_password`.
    - grant the user `minimal_flyway` the required privileges to perform database migrations in all schemas.
    - create a single service user `minimal_svc` with password `your_app_password`.
    - grant the user `minimal_svc` usage right on all the schemas.

## Generate RSA key pair for JWT generation and validation
In a shell that has openssl (gitbash will do the job)
```bash
cd authentication_service/rsa
# generate the private key
openssl genrsa -out private.key 2048
# generate the public key
openssl rsa -in private.key -pubout -out public.key
```

## Service ports
- Authentication Service: 5401 - [Swagger](http://localhost:5401/api/docs/swagger-ui/index.html)
- Minimal Service: 5402 - [Swagger](http://localhost:5402/api/docs/swagger-ui/index.html)
