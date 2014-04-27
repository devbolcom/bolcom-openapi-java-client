package com.bol.api.openapi

import spock.lang.Specification

class QueryOffsetSpec extends Specification {

    def 'Can create offset query value'() {
        given:
        def offset = QueryOffset.builder().offset(100)

        expect:
        offset.toString() == '100'
    }
}
