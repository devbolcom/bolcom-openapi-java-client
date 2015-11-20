package com.bol.openapi;

import com.bol.api.openapi_4_0.SearchResults;

import static com.bol.openapi.QueryOfferType.OfferType.*;
import static com.bol.openapi.QuerySortingMethod.SortingBy.PRICE;
import static com.bol.openapi.QuerySortingMethod.SortingOrder.ASCENDING;
import static com.bol.openapi.QuerySortingMethod.SortingOrder.DESCENDING;

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

    public SearchBuilder dataType(QueryDataType.DataType dataType) {
        dataTypes.add(dataType);
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
        offerTypes.add(QueryOfferType.OfferType.NEW);
        return new SearchBuilder(this);
    }

    public SearchBuilder secondHandOffer() {
        offerTypes.add(SECONDHAND);
        return new SearchBuilder(this);
    }

    public SearchBuilder sort(QuerySortingMethod.SortingBy by) {
        sortingMethod = QuerySortingMethod.builder().by(by).order(ASCENDING);
        return new SearchBuilder(this);
    }

    public SearchBuilder sortDescending(QuerySortingMethod.SortingBy by) {
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

    public SearchBuilder field(QuerySearchField.SearchField field) {
        searchField = QuerySearchField.builder().field(field);
        return new SearchBuilder(this);
    }

    public SearchResults search() {
        return client.getApi().search(
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
