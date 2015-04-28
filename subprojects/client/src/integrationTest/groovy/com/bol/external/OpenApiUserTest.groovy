package com.bol.external

import com.bol.openapi.OpenApiClient
import groovy.transform.CompileStatic
import org.junit.Before
import org.junit.Test

@CompileStatic
class OpenApiUserTest {

    private String apiKey

    @Before
    public void setup() {
        apiKey = System.getenv('OPENAPI_KEY')
        if (!apiKey) {
            apiKey = System.getProperty('OPENAPI_KEY')
        }
        Objects.requireNonNull(apiKey, '''
                   Running from your IDE? Set the OPENAPI_KEY property to your OpenAPI API key
                   Running with Gradle, then the 'openapi.properties' should exist in the root project directory with your OpenAPI API key'''.stripIndent()
        )
    }

    @Test
    public void 'Can use searchBuilder outside the api package'() {
        def openApi = OpenApiClient.withDefaultClient(apiKey)

        assert openApi.searchBuilder().term('harry potter').search()
    }

    @Test
    public void 'Can use listBuilder outside the api package'() {
        def openApi = OpenApiClient.withDefaultClient(apiKey)

        assert openApi.listBuilder().list()
    }
}
