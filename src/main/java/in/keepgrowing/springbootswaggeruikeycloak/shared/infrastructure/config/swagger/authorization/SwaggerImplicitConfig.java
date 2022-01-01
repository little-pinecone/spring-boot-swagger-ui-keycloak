package in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.swagger.authorization;

import in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.security.KeycloakProperties;
import in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.swagger.ApiInfoProvider;
import in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.swagger.SwaggerProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
@AllArgsConstructor
@ConditionalOnProperty(name = "security.config.implicit-flow", havingValue = "true")
public class SwaggerImplicitConfig {

    private static final String OAUTH_SCHEME_NAME = "oAuth";
    private static final String AUTH_URL_FORMAT = "%s/realms/%s/protocol/openid-connect/auth";

    @Bean
    OpenAPI customOpenApi(SwaggerProperties swaggerProperties, KeycloakProperties keycloakProperties,
                          ApiInfoProvider infoProvider) {
        var openAPI = new OpenAPI()
                .info(infoProvider.provide(swaggerProperties));

        addSecurity(openAPI, keycloakProperties);

        return openAPI;
    }

    private void addSecurity(OpenAPI openApi, KeycloakProperties properties) {
        Components components = createComponentsWithSecurityScheme(properties);
        openApi
                .components(components)
                .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME));
    }

    private Components createComponentsWithSecurityScheme(KeycloakProperties properties) {
        SecurityScheme oAuthScheme = createOAuthScheme(properties);

        return new Components()
                .addSecuritySchemes(OAUTH_SCHEME_NAME, oAuthScheme);
    }

    private SecurityScheme createOAuthScheme(KeycloakProperties properties) {
        OAuthFlows flows = createAuthFlows(properties);

        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(flows);
    }

    private OAuthFlows createAuthFlows(KeycloakProperties properties) {
        OAuthFlow implicitFlow = createImplicitFlow(properties);

        return new OAuthFlows()
                .implicit(implicitFlow);
    }

    private OAuthFlow createImplicitFlow(KeycloakProperties properties) {
        var authUrl = String.format(AUTH_URL_FORMAT, properties.getAuthServerUrl(), properties.getRealm());

        return new OAuthFlow()
                .authorizationUrl(authUrl);
    }
}
