package in.keepgrowing.springbootswaggeruikeycloak.products.infrastructure.config;

import in.keepgrowing.springbootswaggeruikeycloak.products.domain.repositories.ProductRepository;
import in.keepgrowing.springbootswaggeruikeycloak.products.infrastructure.repositories.InMemoryProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductBeanConfig {

    @Bean
    public ProductRepository productRepository() {
        return new InMemoryProductRepository();
    }
}
