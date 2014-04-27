Bol.com OpenAPI java client
===========================================

Provides an easy way to communicate with the Bol.com OpenAPI.

The `client` is what you want to use as dependency of your project. The `client` provides the easy way of communicating. See [client README.md](subprojects/client/README.md) on how to use it.

Usage
-----

### Commandline
To be able to compile and test this project, you need to:

- copy the `openapi.properties.sample` file to `openapi.properties`
- fill the properties of the `openapi.properties` file with your personal values

These properties will be used for integration tests.

After that is set-up, you can run the following command to compile and test the project:

    gradlew check

### IDE
To be able to run the integration tests in your IDE, make sure the `OPENAPI_KEY` environment variable is set to your key in your test run.

Requirements
------------
- Java 7+
- A Bol.com developer key (which you can request at https://developers.bol.com)

OpenAPI limitations
-------------------
(based on the Bol.com OpenAPI documentation)

- Max. 2000 requests per 60 minutes
- Session lives for 20 minutes without activity
