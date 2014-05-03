package nl.ikoodi.bol.openapi

import spock.lang.Specification

class QueryIncludeAttributesSpec extends Specification {

    def 'Can include attributes'() {
        given:
        def includeAttributes = QueryIncludeAttributes.builder().include()

        expect:
        includeAttributes.toString() == 'true'
    }

    def 'Can exclude attributes'() {
        given:
        def includeAttributes = QueryIncludeAttributes.builder().exclude()

        expect:
        includeAttributes.toString() == 'false'
    }
}
