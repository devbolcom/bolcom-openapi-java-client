package com.bol.openapi

import spock.lang.Specification
import spock.lang.Unroll

import static com.bol.openapi.QuerySearchField.SearchField.*

class QuerySearchFieldSpec extends Specification {

    @Unroll
    def "Can specify '#field' as specific search field"() {
        given:
        def searchField = QuerySearchField.builder().field(field)

        expect:
        searchField.toString() == result

        where:
        field     || result
        NONE      || 'none'
        AUTHOR    || 'author'
        ARTIST    || 'artist'
        TITLE     || 'title'
        BRAND     || 'brand'
        SONGTITLE || 'songtitle'
        ACTOR     || 'actor'
        DIRECTOR  || 'director'
    }

    def "Can choose to have no search field"() {
        given:
        def types = QuerySearchField.builder()
                .none()

        expect:
        types.toString() == 'none'
    }
}
