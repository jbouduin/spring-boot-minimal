# Local setup (description for Windows)

This page describes the setup of a local, runnable environment.
This setup, which is creating database and application users with unsafe passwords, is __NOT__ to be used for a production environment.

## Prerequisites
- have docker installed
- have a postgreSQL container running

## Create and Initialize postgress database
- open a windows terminal and run 
  - `docker exec -it <name of container> psql -U postgres -c "CREATE DATABASE minimal;`
  - `cd scripts`
  - `initialize_local_postgres.bat`. It will copy [database.init.sql](/scripts/database.init.sql) to the container and execute it. The script performs the following actions:
    - create the schemas `minimal_service`, `translation_service` and  `authorization_service` in the `minimal` database.
    - create a single flyway user `minimal_flyway` with password `your_flyway_password`.
    - grant the user `minimal_flyway` all privileges to perform database migrations in all schemas.
    - create a single service user `minimal_svc` with password `your_app_password`.
    - grant the user `minimal_svc` usage right on all the schemas.

## Reset the database
- open a windows terminal and run 
  - `cd scripts`
  - `reset_local_postgres`. It will copy [database.reset.sql](/scripts/database.reset.sql) to the container and execute it. The script performs the following actions:
    - drop the database `minimal`
    - drop the users `minimal_flyway` and `minimal_svc`
    - recreate the database `minimal`

## Generate RSA key pair for JWT generation and validation
In a shell that has openssl (gitbash will do the job)
```bash
cd authentication_service/rsa
# generate the private key
openssl genrsa -out private.key 2048
# generate the public key
openssl rsa -in private.key -pubout -out public.key
```

## Create some dummy data
Make sure all services are running.

In Powershell
```PS
cd scripts\populate
.\populate.ps1 -u admin -p admin
```

It will 
- create 5 items, with a german and english name and description
- create 5 invoices
- add the 5 items to each of the 5 invoices

## Service ports

- Authentication Service: 5401 - [Swagger](http://localhost:5401/api/docs/swagger-ui/index.html)
- Minimal Service: 5402 - [Swagger](http://localhost:5402/api/docs/swagger-ui/index.html)
- Translation Service: 5403 - [Swagger](http://localhost:5403/api/docs/swagger-ui/index.html)