package com.bol.openapi;

public class QueryProductListType {

    private final String type;

    private QueryProductListType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

    public enum ProductListType {
        DEFAULT("toplist_default"),
        OVERALL("toplist_overall"),
        LAST_WEEK("toplist_last_week"),
        LAST_TWO_MONTHS("toplist_last_two_months"),
        NEW("new"),
        PRE_ORDER("preorder");

        private final String value;

        ProductListType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Builder() {
            // empty to have private constructor
        }

        public QueryProductListType list(ProductListType type) {
            return new QueryProductListType(type.getValue());
        }
    }
}
