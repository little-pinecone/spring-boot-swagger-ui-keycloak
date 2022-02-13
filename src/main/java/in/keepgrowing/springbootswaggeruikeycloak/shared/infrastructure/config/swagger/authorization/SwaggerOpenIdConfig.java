package in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.swagger.authorization;

import in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.security.KeycloakProperties;
import in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.swagger.ApiInfoProvider;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
@AllArgsConstructor
@ConditionalOnProperty(name = "security.config.openid-discovery", havingValue = "true")
public class SwaggerOpenIdConfig {

    private static final String OPEN_ID_SCHEME_NAME = "openId";
    private static final String OPENID_CONFIG_FORMAT = "%s/realms/%s/.well-known/openid-configuration";

    @Bean
    OpenAPI customOpenApi(KeycloakProperties keycloakProperties, ApiInfoProvider infoProvider) {
        return new OpenAPI()
                .info(infoProvider.provide())
                .components(new Components()
                        .addSecuritySchemes(OPEN_ID_SCHEME_NAME, createOpenIdScheme(keycloakProperties)))
                .addSecurityItem(new SecurityRequirement().addList(OPEN_ID_SCHEME_NAME));
    }

    private SecurityScheme createOpenIdScheme(KeycloakProperties properties) {
        String connectUrl = String.format(OPENID_CONFIG_FORMAT, properties.getAuthServerUrl(), properties.getRealm());

        return new SecurityScheme()
                .type(SecurityScheme.Type.OPENIDCONNECT)
                .openIdConnectUrl(connectUrl);
    }
}
