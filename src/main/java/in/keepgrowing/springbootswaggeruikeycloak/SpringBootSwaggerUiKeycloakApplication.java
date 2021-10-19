package in.keepgrowing.springbootswaggeruikeycloak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBootSwaggerUiKeycloakApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSwaggerUiKeycloakApplication.class, args);
	}

}
