package in.keepgrowing.springbootswaggeruikeycloak.products.infrastructure.repositories;

import dev.codesoapbox.dummy4j.Dummy4j;
import in.keepgrowing.springbootswaggeruikeycloak.products.domain.model.Product;
import in.keepgrowing.springbootswaggeruikeycloak.products.domain.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryProductRepository implements ProductRepository {

    private final Dummy4j dummy;
    private final List<Product> products;

    public InMemoryProductRepository() {
        this.dummy = new Dummy4j(123L, null, null);
        this.products = dummy.listOf(20, this::generateProduct);
    }

    private Product generateProduct() {
        return Product.builder()
                .id(dummy.identifier().uuid())
                .name(dummy.lorem().word() + " " + dummy.lorem().word())
                .color(dummy.color().name())
                .ean(dummy.identifier().ean13())
                .countryOfOrigin(dummy.nation().country())
                .price(dummy.finance().priceBuilder().withCurrency("USD").build())
                .availableQuantity(dummy.number().nextInt(1, 200))
                .build();
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        return products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst();
    }

    @Override
    public Optional<Product> save(Product productDetails) {
        productDetails.setId(dummy.identifier().uuid());
        products.add(productDetails);

        return findById(productDetails.getId());
    }
}
