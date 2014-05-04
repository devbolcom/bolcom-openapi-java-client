package nl.ikoodi.bol.openapi

import spock.lang.Specification
import spock.lang.Unroll

import static nl.ikoodi.bol.openapi.QuerySortingMethod.SortingBy.*
import static nl.ikoodi.bol.openapi.QuerySortingMethod.SortingOrder.ASCENDING
import static nl.ikoodi.bol.openapi.QuerySortingMethod.SortingOrder.DESCENDING

class QuerySortingMethodSpec extends Specification {

    @Unroll
    def 'Can fluently specify sorting by "#by" ordered "#order"'() {
        given:
        def method = QuerySortingMethod.builder().by(by).order(order)

        expect:
        method.toString() == queryValue

        where:
        by     | order      || queryValue
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
