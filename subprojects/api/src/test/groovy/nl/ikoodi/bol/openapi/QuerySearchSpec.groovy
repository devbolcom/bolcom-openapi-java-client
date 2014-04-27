package com.bol.api.openapi

import spock.lang.Specification

class QuerySearchSpec extends Specification {

    def 'Add one term results in a single query value'() {
        given:
        def types = QuerySearch.builder()
                .add("potter")
                .create()

        expect:
        types.toString() == 'potter'
    }

    def "Add two terms results in a space-separated query value with two values"() {
        given:
        def types = QuerySearch.builder()
                .add("potter")
                .add("bureaustoel")
                .create()

        expect:
        types.toString() == 'potter bureaustoel'
    }

    def "Add three terms results in a space-separated query value with three values"() {
        given:
        def types = QuerySearch.builder()
                .add("potter")
                .add("bureaustoel")
                .add("printer")
                .create()

        expect:
        types.toString() == 'potter bureaustoel printer'
    }
}
