package hr.products.ms.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import hr.products.ms.dto.ProductRequest;
import hr.products.ms.dto.ProductResponse;
import hr.products.ms.service.ProductService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@code ProductRestController}.
 */
@Tag("ut")
@ExtendWith(MockitoExtension.class)
class ProductRestControllerTest {

    @Mock
    private ProductService productService;

    private ProductRestController controller;

    HttpHeaders headers;

    @BeforeEach
    void init() {
        controller = new ProductRestController(productService);
        headers = new HttpHeaders();
        headers.set("User-ID", "test-user");
    }

    @Test
    void testCreateProduct() {
        ProductRequest request = new ProductRequest();
        request.setName("test-product");
        request.setPrice_eur(new BigDecimal("80.00"));

        ProductResponse expectedResponse = getExpectedResponse();

        when(productService.createProduct(request)).thenReturn(expectedResponse);

        ResponseEntity<ProductResponse> responseEntity = controller.createProduct(request, headers);

        checkResult(responseEntity, expectedResponse);
    }

    @Test
    void testGetSpecificProduct() {
        String productCode = "1234567";

        ProductResponse expectedResponse = getExpectedResponse();

        when(productService.getSpecificProduct(productCode)).thenReturn(expectedResponse);

        ResponseEntity<ProductResponse> responseEntity = controller.getSpecificProduct(productCode, headers);

        checkResult(responseEntity, expectedResponse);
    }

    @Test
    void testGetProductList() {
        BigDecimal price = new BigDecimal("80.00");

        List<ProductResponse> expectedResponseList = List.of(getExpectedResponse());

        when(productService.getProductListByPrice(price)).thenReturn(expectedResponseList);

        ResponseEntity<List<ProductResponse>> responseEntity = controller.getProductList(price, headers);

        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(headers, responseEntity.getHeaders());
        assertEquals(expectedResponseList, responseEntity.getBody());
    }

    @Test
    void testGetProductListByAvailability() {
        Boolean isAvailable = true;

        List<ProductResponse> expectedResponseList = List.of(getExpectedResponse());

        when(productService.getAllProductsByAvailability(isAvailable)).thenReturn(expectedResponseList);

        ResponseEntity<List<ProductResponse>> responseEntity = controller.getProductList(isAvailable, headers);

        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(headers, responseEntity.getHeaders());
        assertEquals(expectedResponseList, responseEntity.getBody());
    }

    private static @NotNull ProductResponse getExpectedResponse() {
        ProductResponse expectedResponse = new ProductResponse();
        expectedResponse.setId(1L);
        expectedResponse.setName("test-product");
        expectedResponse.setPrice_usd(new BigDecimal("100.00"));
        expectedResponse.setPrice_eur(new BigDecimal("80.00"));
        expectedResponse.setCode("TEST");
        expectedResponse.setIs_available(true);
        return expectedResponse;
    }


    private void checkResult(ResponseEntity<ProductResponse> responseEntity, ProductResponse expectedResponse) {
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(headers, responseEntity.getHeaders());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}