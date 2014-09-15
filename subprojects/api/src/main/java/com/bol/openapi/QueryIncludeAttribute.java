package com.bol.openapi;

public class QueryIncludeAttribute {

    private final boolean includeAttributes;

    private QueryIncludeAttribute(boolean includeAttributes) {
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

        public QueryIncludeAttribute include() {
            return new QueryIncludeAttribute(true);
        }

        public QueryIncludeAttribute exclude() {
            return new QueryIncludeAttribute(false);
        }
    }
}
