package com.bol.openapi;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;

public class QueryOfferType {

    private final Set<String> types = new LinkedHashSet<>();

    private QueryOfferType(Set<String> types) {
        this.types.addAll(types);
    }

    @Override
    public String toString() {
        return StringUtils.join(types, ",");
    }

    public enum OfferType {
        ALL("all"),
        BOLCOM("bolcom"),
        CHEAPEST("cheapest"),
        NEW("newoffers"),
        SECONDHAND("2ndHand");

        private final String queryValue;

        OfferType(String queryValue) {
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

        private Set<String> types;

        private Builder() {
            types = new LinkedHashSet<>();
        }

        public Builder add(OfferType type) {
            types.add(type.getQueryValue());
            return this;
        }

        public QueryOfferType create() {
            return new QueryOfferType(types);
        }
    }
}
