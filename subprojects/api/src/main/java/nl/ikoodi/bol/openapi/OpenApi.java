package nl.ikoodi.bol.openapi;

import com.bol.api.openapi_4_0.ListResults;
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
                         @QueryParam("pids") QueryProductId productIds,
                         @QueryParam("ids") QueryCategoryId categoryIds,
                         @QueryParam("dataoutput") QueryDataType dataTypes,
                         @QueryParam("offers") QueryOfferType offerTypes,
                         @QueryParam("sort") QuerySortingMethod sortingMethod,
                         @QueryParam("offset") QueryOffset offset,
                         @QueryParam("limit") QueryLimit limit,
                         @QueryParam("includeattributes") QueryIncludeAttribute includeAttributes,
                         @QueryParam("searchfield") QuerySearchField searchField
    );

    @GET
    @Path("/catalog/v4/lists")
    ListResults list(@QueryParam("type") QueryProductListType type,
                     @QueryParam("ids") QueryCategoryId categoryIds,
                     @QueryParam("dataoutput") QueryDataType dataTypes,
                     @QueryParam("offers") QueryOfferType offerTypes,
                     @QueryParam("sort") QuerySortingMethod sortingMethod,
                     @QueryParam("offset") QueryOffset offset,
                     @QueryParam("limit") QueryLimit limit,
                     @QueryParam("includeattributes") QueryIncludeAttribute includeAttributes
    );

    @GET
    @Path("/catalog/v4/lists")
    ListResults list(@QueryParam("dataoutput") QueryDataType dataTypes);

    @GET
    @Path("/catalog/v4/lists")
    ListResults list(@QueryParam("listId") QueryListId listId,
                     @QueryParam("offset") QueryOffset offset,
                     @QueryParam("limit") QueryLimit limit
    );
}
