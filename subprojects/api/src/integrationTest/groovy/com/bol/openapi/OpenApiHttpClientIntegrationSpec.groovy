package com.bol.openapi

import spock.lang.Specification
import spock.lang.Unroll

import static com.bol.openapi.QueryDataType.DataType.*
import static com.bol.openapi.QueryOfferType.OfferType.ALL
import static com.bol.openapi.QueryOfferType.OfferType.SECONDHAND
import static com.bol.openapi.QueryProductListType.ProductListType.*
import static com.bol.openapi.QuerySortingMethod.SortingBy.PRICE
import static com.bol.openapi.QuerySortingMethod.SortingOrder.ASCENDING
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
                , QueryProductId.builder().none()
                , QueryCategoryId.builder().none()
                , QueryDataType.builder().add(PRODUCTS).create()
                , QueryOfferType.builder().add(SECONDHAND).create()
                , QuerySortingMethod.builder().by(PRICE).order(ASCENDING)
                , QueryOffset.builder().offset(0)
                , QueryLimit.builder().limit(10)
                , QueryIncludeAttribute.builder().exclude()
                , QuerySearchField.builder().none()
        )

        expect:
        results.totalResultSize > 0
        results.products.size() > 0 && results.products.size() <= 10
        results.categories.size() == 0
        results.refinementGroups.size() == 0
    }

    def 'List all categories'() {
        given:
        def client = OpenApiHttpClient.create(apiKey)
        def results = client.list(
                QueryDataType.builder().add(CATEGORIES).create()
        )

        expect:
        results.totalResultSize > 0
        results.products.size() == 0
        results.categories.size() > 0
        results.refinementGroups.size() == 0
    }

    def 'List all refinements'() {
        given:
        def client = OpenApiHttpClient.create(apiKey)
        def results = client.list(
                QueryDataType.builder().add(REFINEMENTS).create()
        )

        expect:
        results.totalResultSize > 0
        results.products.size() == 0
        results.categories.size() == 0
        results.refinementGroups.size() > 0
    }

    def 'List products in a list specified by id'() {
        given:
        def client = OpenApiHttpClient.create(apiKey)
        def results = client.list(
                QueryListId.builder().id('fake_id')
                , QueryOffset.builder().offset(0)
                , QueryLimit.builder().limit(10)
        )

        expect:
        results.totalResultSize > 0
        results.products.size() > 0 && results.products.size() <= 10
        results.categories.size() == 0
        results.refinementGroups.size() == 0
    }

    @Unroll
    def 'List products in a pre-defined list "#listName"'() {
        given:
        def client = OpenApiHttpClient.create(apiKey)
        def results = client.list(
                QueryProductListType.builder().list(listName)
                , QueryCategoryId.builder().none()
                , QueryDataType.builder().add(PRODUCTS).create()
                , QueryOfferType.builder().add(ALL).create()
                , QuerySortingMethod.builder().by(PRICE).order(ASCENDING)
                , QueryOffset.builder().offset(0)
                , QueryLimit.builder().limit(10)
                , QueryIncludeAttribute.builder().exclude()
        )

        expect:
        results.totalResultSize > 0
        results.products.size() > 0 && results.products.size() <= 10
        results.categories.size() == 0
        results.refinementGroups.size() == 0

        where:
        listName        | _
        DEFAULT         | _
        OVERALL         | _
        LAST_WEEK       | _
        LAST_TWO_MONTHS | _
        NEW             | _
        PRE_ORDER       | _
    }
}
