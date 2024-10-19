package edu.costavitor.setanotificationapi.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
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
        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();

        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(
                UriComponentsBuilder.fromHttpUrl(geocodingApiUrl)
                        .queryParam("key", geocodingApiKey));

        return WebClient.builder()
                .exchangeStrategies(strategies)
                .uriBuilderFactory(uriBuilderFactory).build();
    }

    @Bean
    public WebClient ibgeApiWebClient() {
        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();

        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(
                UriComponentsBuilder.fromHttpUrl(ibgeApiUrl));

        return WebClient.builder()
                .exchangeStrategies(strategies)
                .uriBuilderFactory(uriBuilderFactory).build();
    }
}
