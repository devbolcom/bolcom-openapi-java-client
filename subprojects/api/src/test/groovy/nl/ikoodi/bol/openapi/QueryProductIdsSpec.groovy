package com.bol.api.openapi

import spock.lang.Specification

class QueryProductIdsSpec extends Specification {

    def 'Add one id results in a single query value'() {
        given:
        def types = QueryProductIds.builder()
                .add("1234567890")
                .create()

        expect:
        types.toString() == '1234567890'
    }

    def "Add two id's results in a comma-separated query value with two values"() {
        given:
        def types = QueryProductIds.builder()
                .add("1234567890")
                .add("0123456789")
                .create()

        expect:
        types.toString() == '1234567890,0123456789'
    }

    def "Add three id's results in a comma-separated query value with three values"() {
        given:
        def types = QueryProductIds.builder()
                .add("1234567890")
                .add("0123456789")
                .add("9876543210")
                .create()

        expect:
        types.toString() == '1234567890,0123456789,9876543210'
    }

    def "Can choose to have no product id's"() {
        given:
        def types = QueryProductIds.builder()
                .none()

        expect:
        types.toString() == ''
    }
}
