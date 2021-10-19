package in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.swagger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "swagger")
@ConstructorBinding
@AllArgsConstructor
@Getter
public class SwaggerProperties {

    private final String projectTitle;
    private final String projectDescription;
    private final String projectVersion;
}
