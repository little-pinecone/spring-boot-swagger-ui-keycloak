package in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "keycloak")
@ConstructorBinding
@AllArgsConstructor
@Getter
public class KeycloakProperties {

    private final String authServerUrl;
    private final String realm;
}
