package com.bol.api.openapi;

public class QueryIncludeAttributes {

    private final boolean includeAttributes;

    private QueryIncludeAttributes(boolean includeAttributes) {
        this.includeAttributes = includeAttributes;
    }

    @Override
    public String toString() {
        if (includeAttributes) {
            return "true";
        }
        return "false";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Builder() {
            // empty to have private constructor
        }

        public QueryIncludeAttributes include() {
            return new QueryIncludeAttributes(true);
        }

        public QueryIncludeAttributes exclude() {
            return new QueryIncludeAttributes(false);
        }
    }
}
