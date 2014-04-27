package com.bol.api.openapi

import spock.lang.Specification

import static com.bol.api.openapi.QueryDataTypes.DataType.PRODUCTS
import static com.bol.api.openapi.QueryOfferTypes.OfferType.SECONDHAND
import static com.bol.api.openapi.QuerySortingMethod.SortingBy.PRICE
import static com.bol.api.openapi.QuerySortingMethod.SortingOrder.ASCENDING
import static org.hamcrest.Matchers.isEmptyString
import static org.hamcrest.Matchers.not
import static spock.util.matcher.HamcrestSupport.that

class OpenApiHttpClientIntegrationSpec extends Specification {

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

    def 'Ping results in a Pong'() {
        given:
        def client = OpenApiHttpClient.create(apiKey)

        expect:
        def pong = client.ping()
        that pong.message, not(isEmptyString())
    }

    def 'Ping to nonexisting domain results in a failure'() {
        given:
        def client = OpenApiHttpClient.create(apiKey, "https://nonexisting.api.bol.com")

        when:
        def pong = client.ping()

        then:
        thrown(RuntimeException)
    }

    def 'Search 2nd Hand offers of Potter'() {
        given:
        def client = OpenApiHttpClient.create(apiKey)
        def results = client.search(
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

        expect:
        results.totalResultSize > 0
        results.products.size() > 0 && results.products.size() <= 10
        results.categories.size() == 0
        results.refinementGroups.size() == 0
    }
}
