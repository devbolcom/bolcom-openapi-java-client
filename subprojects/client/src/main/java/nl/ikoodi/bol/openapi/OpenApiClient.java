package nl.ikoodi.bol.openapi;

import com.bol.api.openapi_4_0.Pong;

public class OpenApiClient {

    private final OpenApi api;

    public OpenApiClient(OpenApi api) {
        this.api = api;
    }

    public static OpenApiClient withDefaultClient(String key) {
        return new OpenApiClient(OpenApiHttpClient.create(key));
    }

    OpenApi getApi() {
        return api;
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

    public ListBuilder listBuilder() {
        return new ListBuilder(this);
    }
}
