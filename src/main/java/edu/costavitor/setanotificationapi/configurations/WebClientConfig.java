package edu.costavitor.setanotificationapi.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient geocodingApiWebClient() {

        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(
                UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/geocode")
                        .queryParam("key", "key"));

        return WebClient.builder().uriBuilderFactory(uriBuilderFactory).build();
    }

    @Bean
    public WebClient ibgeApiWebClient() {

        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(
                UriComponentsBuilder.fromHttpUrl("https://servicodados.ibge.gov.br/api"));

        return WebClient.builder().uriBuilderFactory(uriBuilderFactory).build();
    }
}
