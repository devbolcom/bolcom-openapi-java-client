package com.bol.openapi.internal.interceptor

import feign.RequestTemplate
import spock.lang.Specification

class OpenApiV4AuthenticationRequestInterceptorSpec extends Specification {

    public static final FAKE_API_KEY = 'myKey'

    def "Adds 'apikey' query param to the request url"() {
        given:
        def template = new RequestTemplate()

        expect:
        new OpenApiV4AuthenticationRequestInterceptor(FAKE_API_KEY).apply(template)
        template.queryLine() == "?apikey=${FAKE_API_KEY}"
    }

    def "Overrides existing 'apikey' query param with the actual apikey"() {
        given:
        def template = new RequestTemplate()
        template.query('apikey', 'randomApiKey')

        expect:
        new OpenApiV4AuthenticationRequestInterceptor(FAKE_API_KEY).apply(template)
        template.queryLine() == "?apikey=${FAKE_API_KEY}"
    }
}
