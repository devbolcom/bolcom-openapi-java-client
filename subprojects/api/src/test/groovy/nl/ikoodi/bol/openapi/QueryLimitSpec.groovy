package com.bol.api.openapi

import spock.lang.Specification

class QueryLimitSpec extends Specification {

    def 'Can create limit query value'() {
        given:
        def limit = QueryLimit.builder().limit(100)

        expect:
        limit.toString() == '100'
    }

    def 'Limit can not exceed a value of 100'() {
        when:
        QueryLimit.builder().limit(101)

        then:
        thrown(IllegalArgumentException)
    }
}
