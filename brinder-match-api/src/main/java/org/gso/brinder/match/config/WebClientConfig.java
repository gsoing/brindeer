package org.gso.brinder.match.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;

@Configuration
public class WebClientConfig {

    @Value("${profile.service.url}")
    private String profileServiceUrl;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(profileServiceUrl)
                .defaultRequest(request ->
                        request.header("Authorization", ReactiveSecurityContextHolder.getContext()
                                .map(ctx -> ctx.getAuthentication().getCredentials().toString())
                                .block()))
                .build();
    }
}
