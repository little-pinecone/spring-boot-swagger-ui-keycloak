package in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config;

import in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.security.SecurityConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "security.config.use-keycloak", havingValue = "false")
public class TestSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SecurityConfig.configureApiSecurity(http);
    }
}
