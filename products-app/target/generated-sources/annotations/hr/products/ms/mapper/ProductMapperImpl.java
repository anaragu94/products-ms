package hr.products.ms.mapper;

import hr.products.ms.dto.ProductResponse;
import hr.products.ms.entity.ProductEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-30T20:46:53+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponse toProductResponse(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.price_eur( productEntity.getPriceEur() );
        productResponse.price_usd( productEntity.getPriceUsd() );
        productResponse.is_available( productEntity.getIsAvailable() );
        productResponse.id( productEntity.getId() );
        productResponse.code( productEntity.getCode() );
        productResponse.name( productEntity.getName() );

        return productResponse.build();
    }
}
