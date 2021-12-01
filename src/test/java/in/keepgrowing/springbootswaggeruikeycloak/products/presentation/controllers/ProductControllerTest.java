package in.keepgrowing.springbootswaggeruikeycloak.products.presentation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.keepgrowing.springbootswaggeruikeycloak.products.domain.model.Product;
import in.keepgrowing.springbootswaggeruikeycloak.products.domain.model.TestProductProvider;
import in.keepgrowing.springbootswaggeruikeycloak.products.domain.repositories.ProductRepository;
import in.keepgrowing.springbootswaggeruikeycloak.products.infrastructure.config.MvcConfig;
import in.keepgrowing.springbootswaggeruikeycloak.shared.infrastructure.annotations.RestControllerIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RestControllerIntegrationTest(value = ProductController.class)
class ProductControllerTest {

    private static final String BASE_PATH = "/" + MvcConfig.API_PREFIX + "/" + ProductControllerPaths.PRODUCTS_PATH;
    private static final String TEST_UUID = "a25fd1a8-b2e2-3b40-97a5-cead9ec87986";

    private TestProductProvider productProvider;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductController controller;

    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productProvider = new TestProductProvider();
    }

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }

    @Test
    @WithMockUser
    void shouldReturnAllProducts() throws Exception {
        Product product = productProvider.full();
        List<Product> products = List.of(product);

        when(productRepository.findAll())
                .thenReturn(products);

        String expectedResponse = objectMapper.writeValueAsString(products);

        mvc.perform(get(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @WithMockUser
    void shouldReturnProductById() throws Exception {
        Product product = productProvider.full();

        when(productRepository.findById(UUID.fromString(TEST_UUID)))
                .thenReturn(Optional.of(product));

        String expectedResponse = objectMapper.writeValueAsString(product);

        mvc.perform(get(BASE_PATH + "/" + TEST_UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @WithMockUser
    void shouldReturnNotFoundForNonExistingId() throws Exception {
        when(productRepository.findById(UUID.fromString(TEST_UUID)))
                .thenReturn(Optional.empty());

        mvc.perform(get(BASE_PATH + "/" + TEST_UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void shouldSaveNewProduct() throws Exception {
        Product productDetails = productProvider.withoutId();
        Product expected = productProvider.full();

        when(productRepository.save(productDetails))
                .thenReturn(Optional.of(expected));

        String expectedResponse = objectMapper.writeValueAsString(expected);

        mvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDetails))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }
}