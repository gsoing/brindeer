package org.gso.brinder.common.configuration;

import org.gso.brinder.common.mvc.RestControllerAdvice;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@Import(RestControllerAdvice.class)
public class CommonConfiguration {
}
