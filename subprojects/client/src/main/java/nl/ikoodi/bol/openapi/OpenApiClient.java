package nl.ikoodi.bol.openapi;

import com.bol.api.openapi_4_0.Pong;
import com.bol.api.openapi_4_0.SearchResults;

import static nl.ikoodi.bol.openapi.QueryOfferTypes.OfferType.*;
import static nl.ikoodi.bol.openapi.QuerySortingMethod.SortingBy.PRICE;
import static nl.ikoodi.bol.openapi.QuerySortingMethod.SortingOrder.ASCENDING;

public class OpenApiClient {

    private final OpenApi api;

    public OpenApiClient(OpenApi api) {
        this.api = api;
    }

    public static OpenApiClient withDefaultClient(String key) {
        return new OpenApiClient(OpenApiHttpClient.create(key));
    }

    public SearchBuilder searchBuilder() {
        return new SearchBuilder(this);
    }

    public HealthStatus getHealthStatus() {
        try {
            Pong pong = api.ping();
            return HealthStatus.builder().isHealthy();
        } catch (RuntimeException ex) {
            return HealthStatus.builder().isUnhealthy(ex.getMessage());
        }
    }

    private class SearchBuilder {
        private static final int OFFSET_DEFAULT = 0;
        private static final int LIMIT_DEFAULT = 10;
        private OpenApiClient client;
        private QuerySearch.Builder query = QuerySearch.builder();
        private QueryProductIds.Builder productIds = QueryProductIds.builder();
        private QueryCategoryIds.Builder categoryIds = QueryCategoryIds.builder();
        private QueryDataTypes.Builder dataTypes = QueryDataTypes.builder();
        private QueryOfferTypes.Builder offerTypes = QueryOfferTypes.builder();
        private QuerySortingMethod sortingMethod = QuerySortingMethod.builder().by(PRICE).order(ASCENDING);
        private QueryOffset offset = QueryOffset.builder().offset(OFFSET_DEFAULT);
        private QueryLimit limit = QueryLimit.builder().limit(LIMIT_DEFAULT);
        private QueryIncludeAttributes includeAttributes = QueryIncludeAttributes.builder().exclude();
        private QuerySearchField searchField = QuerySearchField.builder().none();

        public SearchBuilder(OpenApiClient client) {
            this.client = client;
        }

        public SearchBuilder term(String term) {
            query.add(term);
            return this;
        }

        public SearchBuilder offset(int amount) {
            offset = QueryOffset.builder().offset(amount);
            return this;
        }

        public SearchBuilder limit(int amount) {
            limit = QueryLimit.builder().limit(amount);
            return this;
        }

        public SearchBuilder allOffers() {
            offerTypes.add(ALL);
            return this;
        }

//        Commented because OpenAPI does not make clear where the included attributes reside in the model
//        public SearchBuilder includeProductAttributes() {
//            includeAttributes = QueryIncludeAttributes.builder().include();
//            return this;
//        }

        public SearchBuilder cheapestOffer() {
            offerTypes.add(CHEAPEST);
            return this;
        }

        public SearchBuilder bolOffer() {
            offerTypes.add(BOLCOM);
            return this;
        }

        public SearchBuilder newOffer() {
            offerTypes.add(NEW);
            return this;
        }

        public SearchBuilder secondHandOffer() {
            offerTypes.add(SECONDHAND);
            return this;
        }

        public SearchResults search() {
            return client.api.search(
                    query.create()
                    , productIds.create()
                    , categoryIds.create()
                    , dataTypes.create()
                    , offerTypes.create()
                    , sortingMethod
                    , offset
                    , limit
                    , includeAttributes
                    , searchField
            );
        }
    }
}
