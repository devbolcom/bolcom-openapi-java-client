package nl.ikoodi.bol.openapi

import spock.lang.Specification
import spock.lang.Unroll

import static nl.ikoodi.bol.openapi.QueryProductListType.ProductListType.*

class QueryProductListTypeSpec extends Specification {

    @Unroll
    def 'Can specify product list type "#type"'() {
        given:
        def listType = QueryProductListType.builder().list(type)

        expect:
        listType.toString() == queryValue

        where:
        type            || queryValue
        DEFAULT         || 'toplist_default'
        OVERALL         || 'toplist_overall'
        LAST_WEEK       || 'toplist_last_week'
        LAST_TWO_MONTHS || 'toplist_last_two_months'
        NEW             || 'new'
        PRE_ORDER       || 'preorder'
    }
}
