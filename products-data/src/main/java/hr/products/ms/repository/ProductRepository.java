package hr.products.ms.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hr.products.ms.entity.ProductEntity;

/**
 * Repository interface for Product.
 */
@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByCode(String code);

    List<ProductEntity> findAllByPriceEurGreaterThanEqual(BigDecimal priceEur);

    List<ProductEntity> findAllByIsAvailable(Boolean isAvailable);
}
