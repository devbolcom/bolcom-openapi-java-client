package com.bol.api.openapi;

public class QueryLimit {

    private final int limit;

    private QueryLimit(int offset) {
        this.limit = offset;
    }

    @Override
    public String toString() {
        return String.valueOf(limit);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Builder() {
            // empty to have private constructor
        }

        public QueryLimit limit(int limit) {
            verifyLimit(limit);
            return new QueryLimit(limit);
        }

        private void verifyLimit(int limit) {
            if (limit > 100) {
                throw new IllegalArgumentException("limit may not be larger than 100");
            }
        }
    }
}
