package in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.swagger;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

public class ApiInfoProvider {

    private final SwaggerProperties properties;

    public ApiInfoProvider(SwaggerProperties properties) {
        this.properties = properties;
    }

    public Info provide() {
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
}
