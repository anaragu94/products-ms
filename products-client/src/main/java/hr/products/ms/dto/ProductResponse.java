package hr.products.ms.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data for Product REST Controller response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {

    @JsonProperty("id")
    @NotNull
    private Long id;

    @JsonProperty("code")
    @NotNull
    private String code;

    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("price_eur")
    @NotNull
    @DecimalMin(value = "0")
    @Digits(integer = 17, fraction = 3)
    private BigDecimal price_eur;

    @JsonProperty("price_usd")
    @NotNull
    @DecimalMin(value = "0")
    @Digits(integer = 17, fraction = 3)
    private BigDecimal price_usd;

    @JsonProperty("is_available")
    @NotNull
    private Boolean is_available;
}
