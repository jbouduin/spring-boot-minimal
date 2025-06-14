# Environment files

## Key points
- [Root .env file (.env.shared.dev)](/.env.shared.dev) contains all environment variables used in the application properties files of the services. Some of them are not set and have to be set in the local .env file of the microservice-
- [Service specific .env file](/minimal_service/.env.dev) contains all environment variables used in the application properties file of the particular service. The majority has been defined in the shared .env file and can be overwritten here.

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
Avoid modifying any application.properties file and use the .env files. Even if this means you have to restart the service.