# Authentication and authorization

## Key points
- Authentication using user/password or email/password.
- Authentication service gives a JWT containing the assigned roles.

## Roles
- There are two predefined roles in the system:
  - SYS_ADMIN: this role is marked as system administrator role.
  - USER
- Their values are defined in the enum [SystemRole](/shared_lib/src/main/java/de/der_e_coach/shared_lib/entity/SystemRole.java)

## Migration
- two accounts are created when the authentication services runs for the first time ([V01_00_003__Security_Tables_Data.java](/authentication_service/src/main/java/de/der_e_coach/authentication_service/db/migration/V01_00_003__Security_Tables_Data.java))
  - the system administrator
    - userName is taken from application property `der-e-coach.authentication.first-usage-admin-user`
    - password is taken from application property `der-e-coach.authentication.first-usage-admin-password`
    - receives all roles from [SystemRole](/shared_lib/src/main/java/de/der_e_coach/shared_lib/entity/SystemRole.java)
  - a system account (not sure that we really need 'system'. Originally intended to make flyway call the translation service during migrations)
    - userName is taken from application property `der-e-coach.authentication.system-user`
    - password is taken from application property `der-e-coach.authentication.system-password`
    - receives all roles from [SystemRole](/shared_lib/src/main/java/de/der_e_coach/shared_lib/entity/SystemRole.java)

## Building the FilterSecurityChain using the shared library classes
The shared library contains the [SecurityFilterChainConfiguration](/shared_lib/src/main/java/de/der_e_coach/shared_lib/configuration/SecurityFilterChainConfiguration.java) class, wich creates a SecurityFilterChain bean.
This bean receives the JwtValidationFilter and CorseSecuritySource beans as input. 

The bean from the shared library builds the Security chain as follows:
- add the received CorseSecuritySource to the source, if CORS is enabled in the application property `der-e-coach.security.enable-cors`. If CORS is not enabled, the service will use the default Spring Boot CORS settings.
- disable CSRF if application property `der-e-coach.security.disable-crsf` is set to true.
- if the application property `der-e-coach.security.permit-all-patterns` contains any endpoint, they are added as `permitAll()`
- the actuator endpoint `health` is added as `permitAll()`, using the application property `management.endpoints.web.base-path`.
- the other actuator endpoints are added as `hasAnyRole(adminRoles)`, where adminRoles is calculated using the [SystemRole](/shared_lib/src/main/java/de/der_e_coach/shared_lib/entity/SystemRole.java) enum.
- if the application property `der-e-coach.security.admin-patterns` contains any value, these patterns are also added as `hasAnyRole(adminRoles)`
- the JwtValidationFilter is added to the chain: `.addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class);`.

### JwtValidationFilter
The shared library contains the [JwtValidationFilter](/shared_lib/src/main/java/de/der_e_coach/shared_lib/configuration/JwtValidationFilter.java) interface, which has an implementation [JwtValidationFilterImpl](/shared_lib/src/main/java/de/der_e_coach/shared_lib/configuration/JwtValidationFilterImpl.java) which uses the [Authentication Service Feign client](/shared_lib/src/main/java/de/der_e_coach/shared_lib/service/feign/authentication_service/AuthenticationServiceClient.java) calls the authorization service to validate the token and extract account name and assigned.

The authorization_service itself, has it's own implementation of the interface: see [JwtValidationFilterImpl](/authentication_service/src/main/java/de/der_e_coach/authentication_service/configuration/JwtValidationFilterImpl.java).
Both implementations extend an abstract base class, and only have to override the `protected ResultDto<AuthorizationResultDto> authorize(String token)` method to process the token. In order to allow a service to use it's own JwtValidationFilter implementation, the service may not load the implememtation from the shared library when scanning components:
```Java
@ComponentScan(
    basePackages = "de.der_e_coach", excludeFilters = {
      @ComponentScan.Filter(type = FilterType.REGEX, pattern = "de.der_e_coach.shared_lib.service.feign.*"),
      @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtValidationFilterImpl.class)
    }
)
```
_In this snippet taken from the authorization_service, the feign clients are also excluded._

### CorsConfigurationSource
The [CorsConfigurationSource](/shared_lib/src/main/java/de/der_e_coach/shared_lib/configuration/CorsSecurityConfiguration.java) uses the application properties `der-e-coach.cors.allowed_origin`, `der-e-coach.cors.allowed_methods`, `der-e-coach.cors.allowed_headers` and `der-e-coach.cors.allow_credentials` to configure CORS. 




