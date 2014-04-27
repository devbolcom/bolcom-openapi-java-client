package com.bol.api.openapi;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;

public class QueryProductIds {

    private final Set<String> ids = new LinkedHashSet<>();

    private QueryProductIds(Set<String> ids) {
        this.ids.addAll(ids);
    }

    @Override
    public String toString() {
        return StringUtils.join(ids, ",");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Set<String> ids;

        private Builder() {
            ids = new LinkedHashSet<>();
        }

        public Builder add(String id) {
            ids.add(id);
            return this;
        }

        public QueryProductIds create() {
            return new QueryProductIds(ids);
        }

        public QueryProductIds none() {
            return new QueryProductIds(new LinkedHashSet<String>());
        }
    }
}
