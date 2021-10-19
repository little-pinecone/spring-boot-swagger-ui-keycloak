package in.keepgrowing.springbootswaggeruikeycloak.products.infrastructure.repositories;

import in.keepgrowing.springbootswaggeruikeycloak.products.domain.model.TestProductProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryProductRepositoryTest {

    private InMemoryProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
    }

    @Test
    void shouldReturnListOfProducts() {
        var actual = productRepository.findAll();

        assertNotNull(actual);

        assertAll(
                () -> assertFalse(actual.isEmpty(), "Empty list"),
                () -> assertNotNull(actual.get(0), "List element is null"),
                () -> assertFalse(actual.get(0).getName().isBlank(), "Blank element field")
        );
    }

    @Test
    void shouldReturnProductById() {
        var products = productRepository.findAll();

        var actual = productRepository.findById(products.get(0).getId());

        assertNotNull(actual);
        assertTrue(actual.isPresent());
        assertNotNull(actual.get().getId());
    }

    @Test
    void shouldReturnEmptyOptionalForNotExistingProduct() {
        var invalidId = UUID.fromString("11111111-1111-1111-1111-111111111111");

        var actual = productRepository.findById(invalidId);

        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    void shouldSaveProduct() {
        var originalSize = productRepository.findAll().size();
        var productProvider = new TestProductProvider();
        var productDetails = productProvider.withoutId();

        var actual = productRepository.save(productDetails);

        assertNotNull(actual);
        assertTrue(actual.isPresent());
        assertNotNull(actual.get().getId());
        assertEquals(originalSize + 1, productRepository.findAll().size());
    }

}