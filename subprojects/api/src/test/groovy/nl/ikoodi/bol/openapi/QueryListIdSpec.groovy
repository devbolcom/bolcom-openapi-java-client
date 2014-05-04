package nl.ikoodi.bol.openapi

import spock.lang.Specification

class QueryListIdSpec extends Specification {

    def 'Can specify list id'() {
        given:
        def id = 'fake_id'
        def listId = QueryListId.builder().id(id)

        expect:
        listId.toString() == id
    }
}
