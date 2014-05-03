package nl.ikoodi.bol.openapi

import com.bol.api.openapi_4_0.Pong
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import com.squareup.okhttp.mockwebserver.RecordedRequest
import spock.lang.Specification

import static nl.ikoodi.bol.openapi.internal.interceptor.JsonFormatRequestInterceptor.QUERY_FORMAT_JSON
import static nl.ikoodi.bol.openapi.internal.interceptor.JsonFormatRequestInterceptor.QUERY_FORMAT_KEY
import static nl.ikoodi.bol.openapi.internal.interceptor.OpenApiV4AuthenticationRequestInterceptor.QUERY_AUTH_KEY

class OpenApiHttpClientSpec extends Specification {

    public static final String MOCK_API_KEY = 'mockedApiKey'
    private MockWebServer server
    private api

    def setup() {
        server = new MockWebServer()
        server.play()
        api = createMockedApiClient()
    }

    def cleanup() {
        server.shutdown()
    }

    def 'Api is wrapped with OpenAPI v4 required API key and format=json'() {
        given:
        def response = new Pong()
        jsonResponse(response);

        expect:
        api.ping()

        and:
        def request = server.takeRequest()
        isGetRequestOnPath('/utils/v4/ping', request)
        requestPathContainsApiKey(request)
        requestPathContainsFormatJson(request)
    }

    private boolean isGetRequestOnPath(String requestPath, RecordedRequest request) {
        request.with {
            return method == 'GET' && getRequestPath(path) == requestPath
        }
    }

    private boolean requestPathContainsApiKey(RecordedRequest request) {
        def map = queryMapOf(request.path)
        return map.containsKey(QUERY_FORMAT_KEY) && map.get(QUERY_AUTH_KEY) == MOCK_API_KEY
    }

    private boolean requestPathContainsFormatJson(RecordedRequest request) {
        def map = queryMapOf(request.path)
        return map.containsKey(QUERY_FORMAT_KEY) && map.get(QUERY_FORMAT_KEY) == QUERY_FORMAT_JSON
    }

    private Map queryMapOf(String path) {
        def queryMap = [:]
        String queryLine = getQueryLineOf(path)
        queryLine.split('&').each {
            def (k, v) = it.split('=')
            queryMap.put(k, v)
        }
        return queryMap
    }

    private String getQueryLineOf(String path) {
        def endOfPath = path.indexOf('?')
        if (endOfPath == -1) {
            return path
        }
        return path.substring(endOfPath).stripMargin('?')
    }

    private String getRequestPath(String path) {
        return path.substring(0, path.indexOf('?'))
    }

    private jsonResponse(Object obj) {
        server.enqueue(new MockResponse().setBody(toJson(obj)))
    }

    private OpenApi createMockedApiClient() {
        return OpenApiHttpClient.create(MOCK_API_KEY, server.getUrl('').toString())
    }

    private String toJson(Object obj) {
        def mapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
        return mapper.writeValueAsString(obj)
    }
}
