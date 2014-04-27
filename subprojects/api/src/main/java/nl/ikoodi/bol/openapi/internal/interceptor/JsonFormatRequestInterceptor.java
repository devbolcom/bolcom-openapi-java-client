package com.bol.api.openapi.internal.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class JsonFormatRequestInterceptor implements RequestInterceptor {

    public static final String QUERY_FORMAT_KEY = "format";
    public static final String QUERY_FORMAT_JSON = "json";

    @Override
    public void apply(RequestTemplate template) {
        template.query(QUERY_FORMAT_KEY, QUERY_FORMAT_JSON);
    }
}
