bol.com OpenAPI v4 java client
===========================================

Provides an easy way to communicate with the bol.com OpenAPI v4.

The `client` is what you want to use as dependency of your project. The `client` provides the easy way of communicating. See [client README.md](subprojects/client/README.md) on how to use it.

To get the bol.com OpenAPI client binaries, add the JCenter repository (https://bintray.com/bintray/jcenter) and the following dependency:

- Maven
  
  ```xml
  <dependency>
      <groupId>com.bol.openapi</groupId>
      <artifactId>openapi-java-client</artifactId>
      <version>4.0.0</version>
  </dependency>
  ```
  
- Gradle
  
  ```
  repositories {
      jcenter()
  }
  dependencies {
      compile 'com.bol.openapi:openapi-java-client:4.0.0'
  }
  ```

Requirements
------------
- Java 7+
- A bol.com developer key (which you can request at https://developers.bol.com)

OpenAPI limitations
-------------------
(based on the bol.com OpenAPI documentation)

- Max. 2000 requests per 60 minutes
- Session lives for 20 minutes without activity

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

Todo
====
- Extend to include all OpenAPI v4 functionality
- Backport to OpenAPI v3
