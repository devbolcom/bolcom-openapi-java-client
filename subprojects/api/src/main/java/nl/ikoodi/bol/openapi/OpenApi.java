package com.bol.api.openapi;

import com.bol.api.openapi_4_0.Pong;
import com.bol.api.openapi_4_0.SearchResults;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

public interface OpenApi {

    @GET
    @Path("/utils/v4/ping")
    Pong ping();

    @GET
    @Path("/catalog/v4/search")
    SearchResults search(@QueryParam("q") QuerySearch query,
                         @QueryParam("pids") QueryProductIds productIds,
                         @QueryParam("ids") QueryCategoryIds categoryIds,
                         @QueryParam("dataOutput") QueryDataTypes dataTypes,
                         @QueryParam("offers") QueryOfferTypes offerTypes,
                         @QueryParam("sort") QuerySortingMethod sortingMethod,
                         @QueryParam("offset") QueryOffset offset,
                         @QueryParam("limit") QueryLimit limit,
                         @QueryParam("includeAttributes") QueryIncludeAttributes includeAttributes,
                         @QueryParam("searchField") QuerySearchField searchField
    );
}
