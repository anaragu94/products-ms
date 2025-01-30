package hr.products.ms.mapper;

import hr.products.ms.dto.ProductResponse;
import hr.products.ms.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper class used for mapping Product data.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "productEntity.priceEur", target = "price_eur")
    @Mapping(source = "productEntity.priceUsd", target = "price_usd")
    @Mapping(source = "productEntity.isAvailable", target = "is_available")
    ProductResponse toProductResponse(ProductEntity productEntity);
}
