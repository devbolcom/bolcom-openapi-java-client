package com.bol.api.openapi;

public class QueryOffset {

    private final int offset;

    private QueryOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return String.valueOf(offset);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Builder() {
            // empty to have private constructor
        }

        public QueryOffset offset(int offset) {
            return new QueryOffset(offset);
        }
    }
}
