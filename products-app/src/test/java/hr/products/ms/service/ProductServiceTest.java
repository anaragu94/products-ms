package hr.products.ms.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.web.reactive.function.client.WebClient;

import hr.products.ms.dto.ExchangeRateResponse;
import hr.products.ms.dto.ProductRequest;
import hr.products.ms.dto.ProductResponse;
import hr.products.ms.entity.ProductEntity;
import hr.products.ms.mapper.ProductMapper;
import hr.products.ms.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@code ProductService}.
 */
@Tag("ut")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Captor
    private ArgumentCaptor<String> uriCaptor;

    @Captor
    private ArgumentCaptor<Class<ExchangeRateResponse[]>> classCaptor;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(repository, mapper, webClient);
    }

    @Test
    void testCreateProduct() {
        ProductRequest request = new ProductRequest();
        request.setPrice_eur(new BigDecimal("10.00"));
        request.setName("Test Product");

        ProductEntity productEntityRes = ProductEntity.builder()
            .id(1L)
            .priceEur(request.getPrice_eur())
            .name(request.getName())
            .priceUsd(new BigDecimal("10.42"))
            .build();

        when(repository.save(any(ProductEntity.class))).thenReturn(productEntityRes);
        when(mapper.toProductResponse(any(ProductEntity.class))).thenReturn(new ProductResponse());

        ExchangeRateResponse expectedResponse = ExchangeRateResponse.builder()
            .brojTecajnice("22")
            .datumPrimjene("2025-01-31")
            .drzava("SAD")
            .drzavaIso("USA")
            .kupovniTecaj("1,041900")
            .prodajniTecaj("1,038700")
            .sifraValute("840")
            .srednjiTecaj("1,040300")
            .valuta("USD")
            .build();

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ExchangeRateResponse[].class)).thenReturn(Mono.just(new ExchangeRateResponse[]{
            expectedResponse}));

        ProductResponse response = productService.createProduct(request);

        verify(requestHeadersUriSpec).uri(uriCaptor.capture());
        verify(responseSpec).bodyToMono(classCaptor.capture());

        assertEquals("https://api.hnb.hr/tecajn-eur/v3?valuta=USD", uriCaptor.getValue());
        assertEquals(ExchangeRateResponse[].class, classCaptor.getValue());

        assertEquals(new ProductResponse(), response);
    }

    @Test
    void testGetSpecificProduct() {
        String productName = "Test Product";
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productName);

        when(repository.findByCode(productName)).thenReturn(Optional.of(productEntity));
        when(mapper.toProductResponse(any(ProductEntity.class))).thenReturn(new ProductResponse());

        ProductResponse response = productService.getSpecificProduct(productName);

        assertEquals(new ProductResponse(), response);
    }

    @Test
    void testGetSpecificProductNotFound() {
        String productName = "Nonexistent Product";

        when(repository.findByCode(productName)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getSpecificProduct(productName));
    }

    @Test
    void testGetProductList() {
        BigDecimal price = new BigDecimal("10.00");
        ProductEntity productEntity = new ProductEntity();
        productEntity.setPriceEur(price);

        when(repository.findAllByPriceEurGreaterThanEqual(price)).thenReturn(Collections.singletonList(productEntity));
        when(mapper.toProductResponse(any(ProductEntity.class))).thenReturn(new ProductResponse());

        assertEquals(Collections.singletonList(new ProductResponse()), productService.getProductListByPrice(price));
    }

    @Test
    void testGetProductListNotFound() {
        BigDecimal price = new BigDecimal("100.00");

        when(repository.findAllByPriceEurGreaterThanEqual(price)).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> productService.getProductListByPrice(price));
    }

    @Test
    void testGetAllProductsByAvailability() {
        Boolean isAvailable = true;
        ProductEntity productEntity = new ProductEntity();
        productEntity.setIsAvailable(isAvailable);

        when(repository.findAllByIsAvailable(isAvailable)).thenReturn(Collections.singletonList(productEntity));
        when(mapper.toProductResponse(any(ProductEntity.class))).thenReturn(new ProductResponse());

        List<ProductResponse> response = productService.getAllProductsByAvailability(isAvailable);

        assertEquals(1, response.size());
        assertEquals(new ProductResponse(), response.get(0));
    }

    @Test
    void testGetAllProductsByAvailabilityNotFound() {
        Boolean isAvailable = false;

        when(repository.findAllByIsAvailable(isAvailable)).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> productService.getAllProductsByAvailability(isAvailable));
    }
}