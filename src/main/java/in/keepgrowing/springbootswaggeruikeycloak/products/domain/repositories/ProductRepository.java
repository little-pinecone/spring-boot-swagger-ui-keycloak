package in.keepgrowing.springbootswaggeruikeycloak.products.domain.repositories;

import in.keepgrowing.springbootswaggeruikeycloak.products.domain.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findById(UUID productId);

    Optional<Product> save(Product productDetails);
}
