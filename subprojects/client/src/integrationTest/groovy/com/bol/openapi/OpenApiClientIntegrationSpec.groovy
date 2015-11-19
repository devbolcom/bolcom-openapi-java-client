package com.bol.openapi

import spock.lang.Specification

class OpenApiClientIntegrationSpec extends Specification {

    private String apiKey

    def setup() {
        apiKey = System.getenv('OPENAPI_KEY')
        if (!apiKey) {
            apiKey = System.getProperty('OPENAPI_KEY')
        }
        Objects.requireNonNull(apiKey, '''
                   Running from your IDE? Set the OPENAPI_KEY property to your OpenAPI API key
                   Running with Gradle, then the 'openapi.properties' should exist in the root project directory with your OpenAPI API key'''.stripIndent()
        )
    }

    def 'Search by term'() {
        def results = OpenApiClient.withDefaultClient(apiKey).searchBuilder()
                .term("harry potter")
                .search()

        expect:
        results.totalResultSize > 0
        results.products.size() > 0 && results.products.size() <= 10
        results.categories.size() == 0
        results.refinementGroups.size() == 0
    }

    def 'Search by multiple terms'() {
        def results = OpenApiClient.withDefaultClient(apiKey).searchBuilder()
                .term("harry potter")
                .term("boek")
                .term("deel 3")
                .search()

        expect:
        results.totalResultSize > 0
        results.products.size() > 0 && results.products.size() <= 10
        results.categories.size() == 0
        results.refinementGroups.size() == 0
    }

    def 'Offset'() {
        def results = OpenApiClient.withDefaultClient(apiKey).searchBuilder()
                .term('harry potter')
                .offset(20)
                .search()

        expect:
        results.totalResultSize > 0
        results.products.size() > 0 && results.products.size() <= 10
        results.categories.size() == 0
        results.refinementGroups.size() == 0
    }

    def 'Limit the amount of returned results'() {
        def results = OpenApiClient.withDefaultClient(apiKey).searchBuilder()
                .term('harry potter')
                .limit(20)
                .search()

        expect:
        results.totalResultSize > 0
        results.products.size() <= 20
        results.categories.size() == 0
        results.refinementGroups.size() == 0
    }

    def 'Return all offers of a product'() {
        def results = OpenApiClient.withDefaultClient(apiKey).searchBuilder()
                .term('8718066236500')
                .limit(1)
                .allOffers()
                .search()

        expect:
        results.totalResultSize >= 1
        results.products.size() == 1

        def product = results.products.get(0)
        println product.EAN
        product.offerData.offers.size() >= 1
    }

    def 'Return cheapest offer of a product'() {
        def results = OpenApiClient.withDefaultClient(apiKey).searchBuilder()
                .term('8718066236500')
                .limit(1)
                .cheapestOffer()
                .search()

        expect:
        results.totalResultSize >= 1
        results.products.size() == 1

        def product = results.products.get(0)
        println product.EAN
        product.offerData.offers.size() >= 1
    }

    def 'Can find out if the OpenAPI is healthy'() {
        def status = OpenApiClient.withDefaultClient(apiKey).getHealthStatus()

        expect:
        status.healthy
        status.message == ''
    }

    def 'Can get list of top selling products'() {
        def results = OpenApiClient.withDefaultClient(apiKey).listBuilder()
                .list()

        expect:
        results.totalResultSize > 0
        results.products.size() > 0
        results.categories.size() == 0
        results.refinementGroups.size() == 0
    }

    def 'Can get list of categories'() {
        def results = OpenApiClient.withDefaultClient(apiKey).listBuilder().dataType(QueryDataType.DataType.CATEGORIES)
                .list()

        expect:
        results.totalResultSize > 0
        results.products.size() == 0
        results.categories.size() > 0
        results.refinementGroups.size() == 0
    }

    def 'Can get list of products and refinements'() {
        def results = OpenApiClient.withDefaultClient(apiKey).listBuilder().dataType(QueryDataType.DataType.PRODUCTS).dataType(QueryDataType.DataType.REFINEMENTS)
                .list()

        expect:
        results.totalResultSize > 0
        results.products.size() > 0
        results.categories.size() == 0
        results.refinementGroups.size() > 0
    }
}
