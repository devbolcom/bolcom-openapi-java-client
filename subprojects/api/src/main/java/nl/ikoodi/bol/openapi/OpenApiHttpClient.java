package nl.ikoodi.bol.openapi;

import feign.Feign;
import feign.RequestInterceptor;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.jaxrs.JAXRSModule;
import nl.ikoodi.bol.openapi.internal.interceptor.JsonFormatRequestInterceptor;
import nl.ikoodi.bol.openapi.internal.interceptor.OpenApiV4AuthenticationRequestInterceptor;

import java.util.Arrays;
import java.util.Objects;

public class OpenApiHttpClient {
    public static final String API_URL = "https://api.bol.com";

    private final String url;
    private final String apiKey;

    public static OpenApi create(String apiKey) {
        return new OpenApiHttpClient(API_URL, apiKey).createClient();
    }

    public static OpenApi create(String apiKey, String url) {
        return new OpenApiHttpClient(url, apiKey).createClient();
    }

    private OpenApiHttpClient(String url, String apiKey) {
        Objects.requireNonNull(url, "url must be provided");
        Objects.requireNonNull(apiKey, "apiKey must be provided");
        this.url = url;
        this.apiKey = apiKey;
    }

    private OpenApi createClient() {
        return Feign.builder()
                .contract(new JAXRSModule.JAXRSContract())
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder())
                .requestInterceptors(getRequestInterceptors())
                .target(OpenApi.class, url);
    }

    private Iterable<RequestInterceptor> getRequestInterceptors() {
        return Arrays.asList(
                new OpenApiV4AuthenticationRequestInterceptor(apiKey),
                new JsonFormatRequestInterceptor()
        );
    }
}
