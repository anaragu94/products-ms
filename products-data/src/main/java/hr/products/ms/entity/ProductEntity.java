package hr.products.ms.entity;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class that represents all columns in table product.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class ProductEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    @Size(max = 10)
    private String code;

    private String name;

    @Column(name = "price_eur")
    private BigDecimal priceEur;

    @Column(name = "price_usd")
    private BigDecimal priceUsd;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @PrePersist
    private void generateIdAndCode() {
        this.id = Math.abs(new Random().nextLong());
        this.code = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}
