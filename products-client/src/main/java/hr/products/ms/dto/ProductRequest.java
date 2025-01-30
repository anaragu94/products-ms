package hr.products.ms.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data for Product REST Controller request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName("requestBody")
public class ProductRequest {

    @NotNull
    private String name;

    @NotNull
    @DecimalMin(value = "0")
    @Digits(integer = 17, fraction = 3)
    private BigDecimal price_eur;

    @NotNull
    private Boolean is_available;
}
