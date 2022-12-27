package losnazar.task.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrencyPriceResponseDto {
    private String id;
    private BigDecimal price;
    private String pair;
    private LocalDateTime createdAt;
}
