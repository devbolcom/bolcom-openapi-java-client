Bol.com OpenAPI v4 HTTP Client
=========================

A http client to talk to the Bol.com OpenAPI version 4

OpenApiHttpClient
-------------

The `OpenApiHttpClient` is a raw interface providing a direct wrapper
around the Bol.com OpenAPI.

### Usage

The basic usage of the `OpenApiHttpClient` is clumsy and extensive, but works :)

    OpenApi api = OpenApiHttpClient.create(apiKey)
    SearchResults results = client.search(
            QuerySearch.builder().add("potter").create()
            , QueryProductIds.builder().none()
            , QueryCategoryIds.builder().none()
            , QueryDataTypes.builder().add(PRODUCTS).create()
            , QueryOfferTypes.builder().add(SECONDHAND).create()
            , QuerySortingMethod.builder().by(PRICE).order(ASCENDING)
            , QueryOffset.builder().offset(0)
            , QueryLimit.builder().limit(10)
            , QueryIncludeAttributes.builder().exclude()
            , QuerySearchField.builder().none()
    )

    // do something with the search results

As you can see, builders are provided for the parameters to ease the pain a little bit.

Take a look at the `OpenApiHttpClientSpec` and `OpenApiHttpClientIntegrationSpec` for more examples.
