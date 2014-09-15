package com.bol.openapi;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;

public class QuerySearch {

    private final Set<String> terms = new LinkedHashSet<>();

    private QuerySearch(Set<String> terms) {
        this.terms.addAll(terms);
    }

    @Override
    public String toString() {
        return StringUtils.join(terms, " ");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Set<String> terms;

        private Builder() {
            terms = new LinkedHashSet<>();
        }

        public Builder add(String term) {
            terms.add(term);
            return this;
        }

        public QuerySearch create() {
            return new QuerySearch(terms);
        }
    }
}
