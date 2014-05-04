package nl.ikoodi.bol.openapi;

import com.bol.api.openapi_4_0.ListResults;
import com.bol.api.openapi_4_0.Pong;
import com.bol.api.openapi_4_0.SearchResults;

import static nl.ikoodi.bol.openapi.QueryOfferType.OfferType;
import static nl.ikoodi.bol.openapi.QueryOfferType.OfferType.*;
import static nl.ikoodi.bol.openapi.QueryProductListType.ProductListType;
import static nl.ikoodi.bol.openapi.QueryProductListType.ProductListType.*;
import static nl.ikoodi.bol.openapi.QuerySearchField.SearchField;
import static nl.ikoodi.bol.openapi.QuerySortingMethod.SortingBy;
import static nl.ikoodi.bol.openapi.QuerySortingMethod.SortingBy.PRICE;
import static nl.ikoodi.bol.openapi.QuerySortingMethod.SortingOrder.ASCENDING;
import static nl.ikoodi.bol.openapi.QuerySortingMethod.SortingOrder.DESCENDING;

public class OpenApiClient {

    private final OpenApi api;

    public OpenApiClient(OpenApi api) {
        this.api = api;
    }

    public static OpenApiClient withDefaultClient(String key) {
        return new OpenApiClient(OpenApiHttpClient.create(key));
    }

    public HealthStatus getHealthStatus() {
        try {
            Pong pong = api.ping();
            return HealthStatus.builder().isHealthy();
        } catch (RuntimeException ex) {
            return HealthStatus.builder().isUnhealthy(ex.getMessage());
        }
    }

    public SearchBuilder searchBuilder() {
        return new SearchBuilder(this);
    }

    public class SearchBuilder {
        private static final int OFFSET_DEFAULT = 0;
        private static final int LIMIT_DEFAULT = 10;
        private final OpenApiClient client;
        private QuerySearch.Builder query = QuerySearch.builder();
        private QueryProductId.Builder productIds = QueryProductId.builder();
        private QueryCategoryId.Builder categoryIds = QueryCategoryId.builder();
        private QueryDataType.Builder dataTypes = QueryDataType.builder();
        private QueryOfferType.Builder offerTypes = QueryOfferType.builder();
        private QuerySortingMethod sortingMethod = QuerySortingMethod.builder().by(PRICE).order(ASCENDING);
        private QueryOffset offset = QueryOffset.builder().offset(OFFSET_DEFAULT);
        private QueryLimit limit = QueryLimit.builder().limit(LIMIT_DEFAULT);
        private QueryIncludeAttribute includeAttributes = QueryIncludeAttribute.builder().exclude();
        private QuerySearchField searchField = QuerySearchField.builder().none();

        public SearchBuilder(OpenApiClient client) {
            this.client = client;
        }

        public SearchBuilder(SearchBuilder builder) {
            this.client = builder.client;
            this.query = builder.query;
            this.productIds = builder.productIds;
            this.categoryIds = builder.categoryIds;
            this.dataTypes = builder.dataTypes;
            this.offerTypes = builder.offerTypes;
            this.sortingMethod = builder.sortingMethod;
            this.offset = builder.offset;
            this.limit = builder.limit;
            this.includeAttributes = builder.includeAttributes;
            this.searchField = builder.searchField;
        }

        public SearchBuilder term(String term) {
            query.add(term);
            return new SearchBuilder(this);
        }

        public SearchBuilder product(String id) {
            productIds.add(id);
            return new SearchBuilder(this);
        }

        public SearchBuilder category(String id) {
            categoryIds.add(id);
            return new SearchBuilder(this);
        }

        public SearchBuilder allOffers() {
            offerTypes.add(ALL);
            return new SearchBuilder(this);
        }

        public SearchBuilder cheapestOffer() {
            offerTypes.add(CHEAPEST);
            return new SearchBuilder(this);
        }

        public SearchBuilder bolOffer() {
            offerTypes.add(BOLCOM);
            return new SearchBuilder(this);
        }

        public SearchBuilder newOffer() {
            offerTypes.add(OfferType.NEW);
            return new SearchBuilder(this);
        }

        public SearchBuilder secondHandOffer() {
            offerTypes.add(SECONDHAND);
            return new SearchBuilder(this);
        }

        public SearchBuilder sort(SortingBy by) {
            sortingMethod = QuerySortingMethod.builder().by(by).order(ASCENDING);
            return new SearchBuilder(this);
        }

        public SearchBuilder sortDescending(SortingBy by) {
            sortingMethod = QuerySortingMethod.builder().by(by).order(DESCENDING);
            return new SearchBuilder(this);
        }

        public SearchBuilder offset(int amount) {
            offset = QueryOffset.builder().offset(amount);
            return new SearchBuilder(this);
        }

        public SearchBuilder limit(int amount) {
            limit = QueryLimit.builder().limit(amount);
            return new SearchBuilder(this);
        }

        public SearchBuilder includeAttributes() {
            includeAttributes = QueryIncludeAttribute.builder().include();
            return new SearchBuilder(this);
        }

        public SearchBuilder field(SearchField field) {
            searchField = QuerySearchField.builder().field(field);
            return new SearchBuilder(this);
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

    public ListBuilder listBuilder() {
        return new ListBuilder(this);
    }

    private class ListBuilder {
        private static final int OFFSET_DEFAULT = 0;
        private static final int LIMIT_DEFAULT = 10;
        private final OpenApiClient client;
        private QueryProductListType productListType = QueryProductListType.builder().list(DEFAULT);
        private QueryCategoryId.Builder categoryIds = QueryCategoryId.builder();
        private QueryDataType.Builder dataTypes = QueryDataType.builder();
        private QueryOfferType.Builder offerTypes = QueryOfferType.builder();
        private QuerySortingMethod sortingMethod = QuerySortingMethod.builder().by(PRICE).order(ASCENDING);
        private QueryOffset offset = QueryOffset.builder().offset(OFFSET_DEFAULT);
        private QueryLimit limit = QueryLimit.builder().limit(LIMIT_DEFAULT);
        private QueryIncludeAttribute includeAttributes = QueryIncludeAttribute.builder().exclude();

        private ListBuilder(OpenApiClient client) {
            this.client = client;
        }

        public ListBuilder(ListBuilder builder) {
            this.client = builder.client;
            this.productListType = builder.productListType;
            this.categoryIds = builder.categoryIds;
            this.dataTypes = builder.dataTypes;
            this.offerTypes = builder.offerTypes;
            this.sortingMethod = builder.sortingMethod;
            this.offset = builder.offset;
            this.limit = builder.limit;
            this.includeAttributes = builder.includeAttributes;
        }

        public ListBuilder defaultTopSelling() {
            productListType = QueryProductListType.builder().list(DEFAULT);
            return new ListBuilder(this);
        }

        public ListBuilder lastWeeksTopSelling() {
            productListType = QueryProductListType.builder().list(LAST_WEEK);
            return new ListBuilder(this);
        }

        public ListBuilder lastTwoMonthsTopSelling() {
            productListType = QueryProductListType.builder().list(LAST_TWO_MONTHS);
            return new ListBuilder(this);
        }

        public ListBuilder newTopSelling() {
            productListType = QueryProductListType.builder().list(ProductListType.NEW);
            return new ListBuilder(this);
        }

        public ListBuilder preOrderTopSelling() {
            productListType = QueryProductListType.builder().list(PRE_ORDER);
            return new ListBuilder(this);
        }

        public ListBuilder category(String id) {
            categoryIds.add(id);
            return new ListBuilder(this);
        }

        public ListBuilder allOffers() {
            offerTypes.add(ALL);
            return new ListBuilder(this);
        }

        public ListBuilder cheapestOffer() {
            offerTypes.add(CHEAPEST);
            return new ListBuilder(this);
        }

        public ListBuilder bolOffer() {
            offerTypes.add(BOLCOM);
            return new ListBuilder(this);
        }

        public ListBuilder newOffer() {
            offerTypes.add(OfferType.NEW);
            return new ListBuilder(this);
        }

        public ListBuilder secondHandOffer() {
            offerTypes.add(SECONDHAND);
            return new ListBuilder(this);
        }

        public ListBuilder sort(SortingBy by) {
            sortingMethod = QuerySortingMethod.builder().by(by).order(ASCENDING);
            return new ListBuilder(this);
        }

        public ListBuilder sortDescending(SortingBy by) {
            sortingMethod = QuerySortingMethod.builder().by(by).order(DESCENDING);
            return new ListBuilder(this);
        }

        public ListBuilder offset(int amount) {
            offset = QueryOffset.builder().offset(amount);
            return new ListBuilder(this);
        }

        public ListBuilder limit(int amount) {
            limit = QueryLimit.builder().limit(amount);
            return new ListBuilder(this);
        }

        public ListBuilder includeAttributes() {
            includeAttributes = QueryIncludeAttribute.builder().include();
            return new ListBuilder(this);
        }

        public ListResults list() {
            return client.api.list(
                    productListType
                    , categoryIds.create()
                    , dataTypes.create()
                    , offerTypes.create()
                    , sortingMethod
                    , offset
                    , limit
                    , includeAttributes
            );
        }
    }
}
