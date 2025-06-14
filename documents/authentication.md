# Authentication and authorization

## Key points
- Authentication using user/pwd
- Authentication service gives a JWT containing the assigned roles
- Login using acount name or email address, case insensitive

## Migration
- two users are created
  - admin
  - system (not sure that we really need system. Originally intended to make flyway call the translation service during migrations)

## Authentication and authorization in other services
Required Beans (currently still implemented in every service)
- [Security Configuration](/minimal_service/src/main/java/de/der_e_coach/minimal_service/configuration/SecurityConfiguration.java) uses the JWT filter
- [JwtValidationFilter](/minimal_service/src/main/java/de/der_e_coach/minimal_service/configuration/JwtValidationFilter.java) calls the authentication service using the feign client if the incoming request has an authorization header.
- [Feign client](/minimal_service/src/main/java/de/der_e_coach/minimal_service/service/feign/authentication_service/) calls the authorization service to validate the token and extract account name and assigned roles.
- [Feign client configuration](/minimal_service/src/main/java/de/der_e_coach/minimal_service/service/feign/FeignClientConfiguration.java) Adds an Authorization header of the incoming request, if available, to every feign client request.


