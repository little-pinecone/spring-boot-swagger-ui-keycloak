package in.keepgrowing.springbootswaggeruikeycloak.products.presentation.controllers;

import in.keepgrowing.springbootswaggeruikeycloak.products.domain.model.Product;
import in.keepgrowing.springbootswaggeruikeycloak.products.domain.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = ProductControllerPaths.PRODUCTS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping()
    @Operation(summary = "Return all products")
    public ResponseEntity<List<Product>> findAll() {
        var products = productRepository.findAll();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("{productId}")
    @Operation(summary = "Return a product by its id")
    public ResponseEntity<Product> findById(@PathVariable UUID productId) {
        return productRepository.findById(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('chief-operating-officer')")
    @Operation(summary = "Save a product (available for the chief-operating-officer role, default user: 'christina')")
    public ResponseEntity<Product> save(@RequestBody Product productDetails) {
        return productRepository.save(productDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}
