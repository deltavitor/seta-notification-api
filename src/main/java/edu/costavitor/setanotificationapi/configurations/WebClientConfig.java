package edu.costavitor.setanotificationapi.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class WebClientConfig {

    @Value("${seta.webclients.geocoding-api.url}")
    private String geocodingApiUrl;

    @Value("${seta.webclients.geocoding-api.key}")
    private String geocodingApiKey;

    @Value("${seta.webclients.ibge.url}")
    private String ibgeApiUrl;

    @Bean
    public WebClient geocodingApiWebClient() {

        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(
                UriComponentsBuilder.fromHttpUrl(geocodingApiUrl)
                        .queryParam("key", geocodingApiKey));

        return WebClient.builder().uriBuilderFactory(uriBuilderFactory).build();
    }

    @Bean
    public WebClient ibgeApiWebClient() {

        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(
                UriComponentsBuilder.fromHttpUrl(ibgeApiUrl));

        return WebClient.builder().uriBuilderFactory(uriBuilderFactory).build();
    }
}
