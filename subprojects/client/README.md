Bol.com OpenAPI v4 Client
=========================

A complete and simple way to talk to the Bol.com OpenAPI version 4

OpenApiClient
-------------

The `OpenApiClient` is the user-friendly interface by the use of builders with
fluent interfaces, smart objects and clean error handling. The `OpenApiClient`
depends on a `OpenApi` implementation of which a default implementation is provided.

### Usage

The basic usage of the `OpenApiClient` is simple and self explanatory:

    OpenApiClient client = OpenApiClient.withDefaultClient(apiKey);
    SearchResults results = client.searchBuilder()
            .term("harry potter)
            .term("boek")
            .search();

    // do something with the search results

Take a look at the `OpenApiClientIntegrationSpec` for more examples.
