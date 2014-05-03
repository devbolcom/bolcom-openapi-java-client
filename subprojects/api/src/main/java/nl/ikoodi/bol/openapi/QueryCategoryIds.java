package nl.ikoodi.bol.openapi;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;

public class QueryCategoryIds {

    private final Set<String> ids = new LinkedHashSet<>();

    private QueryCategoryIds(Set<String> ids) {
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

        public QueryCategoryIds create() {
            return new QueryCategoryIds(ids);
        }

        public QueryCategoryIds none() {
            return new QueryCategoryIds(new LinkedHashSet<String>());
        }
    }
}
