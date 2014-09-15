package com.bol.openapi;

public class QuerySortingMethod {

    private final String sortingMethod;

    private QuerySortingMethod(String sortingMethod) {
        this.sortingMethod = sortingMethod;
    }

    @Override
    public String toString() {
        return sortingMethod;
    }

    public enum SortingBy {
        RANK("rank"),
        PRICE("price"),
        TITLE("title"),
        DATE("date"),
        RATING("rating");

        private final String value;

        SortingBy(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum SortingOrder {
        ASCENDING("asc"),
        DESCENDING("desc");

        private final String value;

        SortingOrder(String value) {
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

        public SortBuilder by(SortingBy by) {
            return new SortBuilder(by);
        }
    }

    public static class SortBuilder {

        private final SortingBy by;

        private SortBuilder(SortingBy by) {
            this.by = by;
        }

        public QuerySortingMethod order(SortingOrder order) {
            final StringBuilder sortingMethod = new StringBuilder();
            sortingMethod.append(by.getValue());
            sortingMethod.append(order.getValue());
            return new QuerySortingMethod(sortingMethod.toString());
        }
    }
}
