package hr.products.ms.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.products.ms.dto.ProductRequest;
import hr.products.ms.dto.ProductResponse;
import hr.products.ms.service.ProductService;
import lombok.extern.slf4j.Slf4j;

/**
 * Rest controller for POST and GET operations for product entity.
 */
@Slf4j
@RestController
@RequestMapping(path = {"/api/product"})
public class ProductRestController {

    private final ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * REST {@code POST} endpoint used for creating a product.
     *
     * @param request Product request
     * @param headers HttpHeaders
     * @return ResponseEntity with ProductResponse
     */
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
        @RequestBody ProductRequest request,
        @RequestHeader HttpHeaders headers
    ) {
        log.info("Received request to create product: {}", request);
        ProductResponse response = null;
        try {
            response = productService.createProduct(request);
        } catch (IllegalArgumentException e) {
            log.error("Error creating product", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(response);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body(response);
    }

    /**
     * REST {@code GET} endpoint used for getting a specific product by code.
     *
     * @param code    Product code
     * @param headers HttpHeaders
     * @return ResponseEntity with ProductResponse
     */
    @GetMapping(path = "/{code}")
    public ResponseEntity<ProductResponse> getSpecificProduct(
        @PathVariable String code,
        @RequestHeader HttpHeaders headers
    ) {
        log.info("Received request to get product with code: {}", code);
        ProductResponse response = productService.getSpecificProduct(code);
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body(response);
    }

    /**
     * REST {@code GET} endpoint used for getting a list of products that are greater or equal than given price in euros.
     *
     * @param price   product price
     * @param headers HttpHeaders
     * @return ResponseEntity with List of ProductResponse
     */
    @GetMapping(path = "/list/{price}")
    public ResponseEntity<List<ProductResponse>> getProductList(
        @PathVariable("price") BigDecimal price,
        @RequestHeader HttpHeaders headers
    ) {
        log.info("Received request to get list of products for given price: {}", price);
        List<ProductResponse> responseList = productService.getProductListByPrice(price);
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body(responseList);
    }


    /**
     * REST {@code GET} endpoint used for getting products that are available or not available, depending on
     * given value.
     *
     * @param isAvailable boolean value for availability
     * @param headers HttpHeaders
     * @return ResponseEntity with List of ProductResponse
     */
    @GetMapping(path = "/available/{isAvailable}")
    public ResponseEntity<List<ProductResponse>> getProductList(
        @PathVariable("isAvailable") Boolean isAvailable,
        @RequestHeader HttpHeaders headers
    ) {
        log.info("Received request to get list of products for given availability: {}", isAvailable);
        List<ProductResponse> responseList = productService.getAllProductsByAvailability(isAvailable);
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body(responseList);
    }
}
