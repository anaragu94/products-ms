package hr.products.ms.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import hr.products.ms.dto.ExchangeRateResponse;
import hr.products.ms.dto.ProductRequest;
import hr.products.ms.dto.ProductResponse;
import hr.products.ms.entity.ProductEntity;
import hr.products.ms.mapper.ProductMapper;
import hr.products.ms.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Service that contains all logic related to product data (creating, getting).
 */
@Service
@Slf4j
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final WebClient webClient;

    /**
     * Class constructor.
     *
     * @param repository repository
     * @param mapper mapper
     * @param webClient web client
     */
    @Autowired
    public ProductService(ProductRepository repository, ProductMapper mapper, WebClient webClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.webClient = webClient;
    }


    /**
     * Method used for creating/adding new product to the database.
     *
     * @param request ProductRequest
     * @return ProductResponse
     */
    public ProductResponse createProduct(ProductRequest request) {
        validatePrice(request.getPrice_eur());

        ProductEntity productEntityReq = ProductEntity.builder()
            .priceEur(request.getPrice_eur())
            .name(request.getName())
            .isAvailable(request.getIs_available())
            .priceUsd(convertToUsd(request.getPrice_eur())).build();
         ProductEntity productEntityRes = repository.save(productEntityReq);
        return mapper.toProductResponse(productEntityRes);
    }


    /**
     * Method that returns a specific product by code.
     *
     * @param code product code
     * @return ProductResponse
     */
    public ProductResponse getSpecificProduct(String code) {
        Optional<ProductEntity> product = repository.findByCode(code);

        if (product.isEmpty()) {
            log.error("Product with code {} not found", code);
            throw new RuntimeException("Product not found");
        } else {
            return mapper.toProductResponse(product.get());
        }
    }

    /**
     * Method that returns a list of products with price greater than or equal to the given price.
     * @param price product price
     * @return List of ProductResponse
     */
    public List<ProductResponse> getProductListByPrice(BigDecimal price) {
        List<ProductEntity> products = repository.findAllByPriceEurGreaterThanEqual(price);

        if(products.isEmpty()) {
            log.error("No products found for price {}", price);
            throw new RuntimeException("No products found");
        } else {
            return products.stream().map(mapper::toProductResponse).collect(Collectors.toList());
        }
    }

    /**
     * Method that returns a list of available or unavailable products depending on param.
     *
     * @param isAvailable true if available, false if unavailable
     * @return List of ProductResponse
     */
    public List<ProductResponse> getAllProductsByAvailability(Boolean isAvailable) {
        List<ProductEntity> products = repository.findAllByIsAvailable(isAvailable);

        if(products.isEmpty()) {
            log.error("No products found for isAvailable {}", isAvailable);
            throw new RuntimeException("No products found");
        } else {
            return products.stream().map(mapper::toProductResponse).collect(Collectors.toList());
        }
    }

    private BigDecimal convertToUsd(BigDecimal priceEur) {
        BigDecimal conversionRate = getUsdConversionRate();
        return priceEur.multiply(conversionRate);
    }

    private BigDecimal getUsdConversionRate() {
        String url = "https://api.hnb.hr/tecajn-eur/v3?valuta=USD";
        ExchangeRateResponse[] response = webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(ExchangeRateResponse[].class)
            .block();
        if (response != null && response.length > 0) {
            return new BigDecimal(response[0].getSrednjiTecaj().replace(",", "."));
        } else {
           return new BigDecimal("1,042100");
        }
    }

    private void validatePrice(BigDecimal priceEur) {
        if (priceEur.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price has to be larger or equal to zero!");
        }
    }
}