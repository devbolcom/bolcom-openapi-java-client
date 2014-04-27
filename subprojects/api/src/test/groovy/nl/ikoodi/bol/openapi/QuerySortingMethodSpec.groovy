package com.bol.api.openapi

import spock.lang.Specification
import spock.lang.Unroll

import static com.bol.api.openapi.QuerySortingMethod.SortingBy.*
import static com.bol.api.openapi.QuerySortingMethod.SortingOrder.ASCENDING
import static com.bol.api.openapi.QuerySortingMethod.SortingOrder.DESCENDING

class QuerySortingMethodSpec extends Specification {

    @Unroll
    def 'Can fluently specify sorting by "#by" ordered "#order"'() {
        given:
        def method = QuerySortingMethod.builder().by(by).order(order)

        expect:
        method.toString() == result

        where:
        by | order || result
        RANK   | ASCENDING  || 'rankasc'
        RANK   | DESCENDING || 'rankdesc'
        PRICE  | ASCENDING  || 'priceasc'
        PRICE  | DESCENDING || 'pricedesc'
        TITLE  | ASCENDING  || 'titleasc'
        TITLE  | DESCENDING || 'titledesc'
        DATE   | ASCENDING  || 'dateasc'
        DATE   | DESCENDING || 'datedesc'
        RATING | ASCENDING  || 'ratingasc'
        RATING | DESCENDING || 'ratingdesc'
    }
}
