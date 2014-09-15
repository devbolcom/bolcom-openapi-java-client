package com.bol.openapi

import spock.lang.Specification

import static com.bol.openapi.QueryOfferType.OfferType.*

class QueryOfferTypesSpec extends Specification {

    def 'Add one type results in a single query value'() {
        given:
        def types = QueryOfferType.builder()
                .add(CHEAPEST)
                .create()

        expect:
        types.toString() == 'cheapest'
    }

    def 'Add two types results in a comma-separated query value with two values'() {
        given:
        def types = QueryOfferType.builder()
                .add(CHEAPEST)
                .add(ALL)
                .create()

        expect:
        types.toString() == 'cheapest,all'
    }

    def 'Add three types results in a comma-separated query value with three values'() {
        given:
        def types = QueryOfferType.builder()
                .add(CHEAPEST)
                .add(SECONDHAND)
                .add(ALL)
                .create()

        expect:
        types.toString() == 'cheapest,2ndHand,all'
    }
}
