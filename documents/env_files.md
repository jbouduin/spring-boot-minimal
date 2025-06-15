# Environment files

## Key points
- [Root .env file (.env.shared.dev)](/.env.shared.dev) contains all environment variables used in the application properties files of the services. Some of them are not set and have to be set in the local .env file of the microservice-
- [Service specific .env file](/minimal_service/.env.dev) contains all environment variables used in the application properties file of the particular service. The majority has been defined in the shared .env file and can be overwritten here.
- All services have almost exactly the same application.properties:
  - The only property that is different is the `spring.application.name=Translation Service` property.
  - Any service can have additional properties that are specific for that service.

## Launch setup (VS Code)
Both .env files (the shared one and the service one have to be specified in the launch configuration)
e.g.

```JSON
    {
      "type": "java",
      "name": "Spring Boot-AuthenticationServiceApplication<authentication_service>",
      "request": "launch",
      "cwd": "${workspaceFolder}",
      "mainClass": "de.der_e_coach.authentication_service.AuthenticationServiceApplication",
      "projectName": "authentication_service",
      "args": "",
      "envFile": [        
        "${workspaceFolder}/.env.shared.dev",
        "${workspaceFolder}/authentication_service/.env.dev"
      ]
    },
```
# Best practice
Avoid modifying any application.properties file and use the .env files. Even if this means you have to restart the service. This way it is ensured that all services have the same application.properties file.

# Contents of the .env.shared file
| Variable Name | Application properties | Application Property default | Usage |
|-|-|-|-|
|||||

# Specific Contents of the .env file of the authorization service
| Variable Name | Application properties | Application Property default | Usage |
|-|-|-|-|
|||||