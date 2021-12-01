package in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.swagger;

import in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.security.KeycloakProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
@AllArgsConstructor
public class SwaggerConfig {

    private static final String OPEN_ID_SCHEME_NAME = "openId";
    private static final String OPENID_CONFIG_FORMAT = "%s/realms/%s/.well-known/openid-configuration";

    @Bean
    OpenAPI customOpenApi(SwaggerProperties swaggerProperties, KeycloakProperties keycloakProperties) {
        var openAPI = new OpenAPI()
                .info(getInfo(swaggerProperties));

        addSecurity(openAPI, keycloakProperties);

        return openAPI;
    }

    private Info getInfo(SwaggerProperties properties) {
        return new Info()
                .title(properties.getProjectTitle())
                .description(properties.getProjectDescription())
                .version(properties.getProjectVersion())
                .license(getLicense());
    }

    private License getLicense() {
        return new License()
                .name("Unlicense")
                .url("https://unlicense.org/");
    }

    private void addSecurity(OpenAPI openApi, KeycloakProperties properties) {
        Components components = createComponentsWithSecurityScheme(properties);
        openApi
                .components(components)
                .addSecurityItem(new SecurityRequirement().addList(OPEN_ID_SCHEME_NAME));
    }

    private Components createComponentsWithSecurityScheme(KeycloakProperties properties) {
        SecurityScheme openIdScheme = createOpenIdScheme(properties);
        Components components = new Components();
        components.addSecuritySchemes(OPEN_ID_SCHEME_NAME, openIdScheme);

        return components;
    }

    private SecurityScheme createOpenIdScheme(KeycloakProperties properties) {
        String connectUrl = String.format(OPENID_CONFIG_FORMAT, properties.getAuthServerUrl(), properties.getRealm());

        return new SecurityScheme()
                .type(SecurityScheme.Type.OPENIDCONNECT)
                .openIdConnectUrl(connectUrl);
    }
}
