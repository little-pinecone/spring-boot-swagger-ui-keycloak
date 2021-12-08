package in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@TestConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ControllerIntegrationTestConfig {
}
