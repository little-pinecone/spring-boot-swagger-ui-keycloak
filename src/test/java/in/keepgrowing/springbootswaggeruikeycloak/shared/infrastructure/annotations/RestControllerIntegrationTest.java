package in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.annotations;

import in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.config.ControllerIntegrationTestConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WebMvcTest
@ActiveProfiles("test")
@ContextConfiguration(classes = ControllerIntegrationTestConfig.class)
public @interface RestControllerIntegrationTest {

    /**
     * @see WebMvcTest#value
     */
    @AliasFor(annotation = WebMvcTest.class, attribute = "value")
    Class<?>[] value() default {};

    /**
     * @see WebMvcTest#controllers
     */
    @AliasFor(annotation = WebMvcTest.class, attribute = "controllers")
    Class<?>[] controllers() default {};
}
