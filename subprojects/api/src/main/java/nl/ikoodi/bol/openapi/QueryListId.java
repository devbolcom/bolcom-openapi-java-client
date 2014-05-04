package nl.ikoodi.bol.openapi;

public class QueryListId {

    private final String id;

    private QueryListId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Builder() {
            // empty to have private constructor
        }

        public QueryListId id(String id) {
            return new QueryListId(id);
        }
    }
}
