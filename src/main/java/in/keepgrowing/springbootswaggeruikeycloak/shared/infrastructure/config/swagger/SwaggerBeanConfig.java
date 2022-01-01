package in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerBeanConfig {

    @Bean
    ApiInfoProvider apiInfoProvider(SwaggerProperties swaggerProperties) {
        return new ApiInfoProvider(swaggerProperties);
    }
}
