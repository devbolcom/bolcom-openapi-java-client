package com.bol.openapi;

import com.bol.api.openapi_4_0.ListResults;

import static com.bol.openapi.QueryOfferType.OfferType.*;
import static com.bol.openapi.QueryProductListType.ProductListType.*;
import static com.bol.openapi.QuerySortingMethod.SortingBy.PRICE;
import static com.bol.openapi.QuerySortingMethod.SortingOrder.ASCENDING;
import static com.bol.openapi.QuerySortingMethod.SortingOrder.DESCENDING;

public class ListBuilder {
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

    public ListBuilder(OpenApiClient client) {
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
        productListType = QueryProductListType.builder().list(QueryProductListType.ProductListType.NEW);
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
        offerTypes.add(QueryOfferType.OfferType.NEW);
        return new ListBuilder(this);
    }

    public ListBuilder secondHandOffer() {
        offerTypes.add(SECONDHAND);
        return new ListBuilder(this);
    }

    public ListBuilder sort(QuerySortingMethod.SortingBy by) {
        sortingMethod = QuerySortingMethod.builder().by(by).order(ASCENDING);
        return new ListBuilder(this);
    }

    public ListBuilder sortDescending(QuerySortingMethod.SortingBy by) {
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
        return client.getApi().list(
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
