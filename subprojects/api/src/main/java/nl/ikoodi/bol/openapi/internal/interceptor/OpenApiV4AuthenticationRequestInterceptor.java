package nl.ikoodi.bol.openapi.internal.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Objects;

public class OpenApiV4AuthenticationRequestInterceptor implements RequestInterceptor {

    public static final String QUERY_AUTH_KEY = "apikey";
    private final String apiKey;

    public OpenApiV4AuthenticationRequestInterceptor(String apiKey) {
        this.apiKey = Objects.requireNonNull(apiKey);
    }

    @Override
    public void apply(RequestTemplate template) {
        template.query(QUERY_AUTH_KEY, apiKey);
    }
}
