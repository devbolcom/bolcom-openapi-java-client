package com.bol.external

import com.bol.openapi.OpenApiClient
import spock.lang.Specification

class OpenApiUserSpec extends Specification {

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

    def 'Can use searchBuilder outside the api package'() {
        def openApi = OpenApiClient.withDefaultClient(apiKey)

        expect:
        def results = openApi.searchBuilder().term('harry potter').search()
        results.products
    }

    def 'Can use listBuilder outside the api package'() {
        def openApi = OpenApiClient.withDefaultClient(apiKey)

        expect:
        def results = openApi.listBuilder().list()
        results.products
    }
}
