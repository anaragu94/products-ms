package hr.products.ms.mapper;

import java.math.BigDecimal;

import hr.products.ms.dto.ProductResponse;
import hr.products.ms.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@code ProductMapper}.
 */
@Tag("ut")
@ExtendWith(MockitoExtension.class)
class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = ProductMapper.INSTANCE;
    }

    @Test
    void testToProductResponse() {
        ProductEntity productEntity = ProductEntity.builder()
            .id(1L)
            .code("TEST")
            .name("test-product")
            .priceEur(new BigDecimal("80.00"))
            .priceUsd(new BigDecimal("100.00"))
            .isAvailable(true)
            .build();

        ProductResponse productResponse = productMapper.toProductResponse(productEntity);

        assertEquals(productEntity.getId(), productResponse.getId());
        assertEquals(productEntity.getCode(), productResponse.getCode());
        assertEquals(productEntity.getName(), productResponse.getName());
        assertEquals(productEntity.getPriceEur(), productResponse.getPrice_eur());
        assertEquals(productEntity.getPriceUsd(), productResponse.getPrice_usd());
        assertEquals(productEntity.getIsAvailable(), productResponse.getIs_available());
    }
}