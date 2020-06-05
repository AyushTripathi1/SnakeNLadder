package com.kidex.SnakeNLadder.configuration;

import lombok.extern.log4j.Log4j2;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import org.apache.http.client.HttpClient;
import java.util.List;

@Log4j2
@Configuration
public class AppConfiguration {

    @Value("${httpClient.connection.pool.size:100}")
    private String poolMaxTotal;

    @Value("${httpClientFactory.connection.timeout:50000}")
    private String connectionTimeOut;

    @Value("${httpClientFactory.read.timeout:10000}")
    private String readTimeOut;

    @Bean
    public RestTemplate restTemplate() {
        return restTemplate(Integer.parseInt(connectionTimeOut), Integer.parseInt(readTimeOut),
                Integer.parseInt(poolMaxTotal));
    }

    public HttpClient httpClient(final int noOfConnections) {
        PoolingHttpClientConnectionManager
                connectionManager =
                new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(noOfConnections);
        return HttpClientBuilder.create().setConnectionManager(connectionManager).build();
    }

    public ClientHttpRequestFactory httpRequestFactory(final int connectionTimeout,
            final int readTimeout, final int maxConnections) {
        HttpComponentsClientHttpRequestFactory
                factory =
                new HttpComponentsClientHttpRequestFactory(httpClient(maxConnections));
        factory.setConnectTimeout(connectionTimeout);
        factory.setReadTimeout(readTimeout);
        return factory;
    }

    public RestTemplate restTemplate(final int connectionTimeout, final int readTimeout,
            final int maxConnections) {
        RestTemplate
                template =
                new RestTemplate(
                        httpRequestFactory(connectionTimeout, readTimeout, maxConnections));
        List<HttpMessageConverter<?>> messageConverters = template.getMessageConverters();
        messageConverters.add(new FormHttpMessageConverter());
        template.setMessageConverters(messageConverters);
        return template;
    }

}
