package com.bol.api.openapi;

import static com.bol.api.openapi.QuerySearchField.SearchField.NONE;

public class QuerySearchField {

    private final SearchField field;

    private QuerySearchField(SearchField field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return field.getQueryValue();
    }

    public enum SearchField {
        NONE("none"),
        AUTHOR("author"),
        ARTIST("artist"),
        TITLE("title"),
        BRAND("brand"),
        SONGTITLE("songtitle"),
        ACTOR("actor"),
        DIRECTOR("director");

        private final String queryValue;

        SearchField(String queryValue) {
            this.queryValue = queryValue;
        }

        public String getQueryValue() {
            return queryValue;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Builder() {
            // empty to have private constructor
        }

        public QuerySearchField field(SearchField field) {
            return new QuerySearchField(field);
        }

        public QuerySearchField none() {
            return new QuerySearchField(NONE);
        }
    }
}
