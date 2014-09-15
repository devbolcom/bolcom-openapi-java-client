package com.bol.openapi.internal.interceptor

import feign.RequestTemplate
import spock.lang.Specification

class JsonFormatRequestInterceptorSpec extends Specification {

    def "Adds 'format=json' to the request url"() {
        given:
        def template = new RequestTemplate()

        expect:
        new JsonFormatRequestInterceptor().apply(template)
        template.queryLine() == '?format=json'
    }

    def "Overrides existing 'format' query param to be format=json"() {
        given:
        def template = new RequestTemplate()
        template.query('format', 'xml')

        expect:
        new JsonFormatRequestInterceptor().apply(template)
        template.queryLine() == '?format=json'
    }
}
