package in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.swagger;

import in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.security.KeycloakProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
@AllArgsConstructor
@ConditionalOnProperty(name = "security.config.authcode-flow", havingValue = "true")
public class SwaggerAuthorizationCodeConfig {

    private static final String OAUTH_SCHEME_NAME = "oAuth";
    private static final String AUTH_URL_FORMAT = "%s/realms/%s/protocol/openid-connect";

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
        Components components = new Components();
        components.addSecuritySchemes(OAUTH_SCHEME_NAME, oAuthScheme);

        return components;
    }

    private SecurityScheme createOAuthScheme(KeycloakProperties properties) {
        OAuthFlows flows = createAuthFlows(properties);

        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(flows);
    }

    private OAuthFlows createAuthFlows(KeycloakProperties properties) {
        OAuthFlow authCodeFlow = createAuthCodeFlow(properties);
        OAuthFlows flows = new OAuthFlows();
        flows.authorizationCode(authCodeFlow);

        return flows;
    }

    private OAuthFlow createAuthCodeFlow(KeycloakProperties properties) {
        var authUrl = String.format(AUTH_URL_FORMAT, properties.getAuthServerUrl(), properties.getRealm());

        return new OAuthFlow()
                .authorizationUrl(authUrl + "/auth")
                .tokenUrl(authUrl + "/token")
                .scopes(new Scopes());
    }
}
