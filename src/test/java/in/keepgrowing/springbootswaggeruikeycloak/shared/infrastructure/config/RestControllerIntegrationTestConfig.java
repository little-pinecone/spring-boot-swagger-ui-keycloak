package in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config;

import org.keycloak.adapters.springboot.KeycloakAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({KeycloakAutoConfiguration.class})
public class RestControllerIntegrationTestConfig {
}
