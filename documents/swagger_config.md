# Swagger configuration bean
Implemented in every service. When moved to the shared library, deserialization goes wrong as multiple deserialization beans are created.

e.g. [OpenApiConfiguration.java](/minimal_service/src/main/java/de/der_e_coach/minimal_service/configuration/OpenApiConfig.java)