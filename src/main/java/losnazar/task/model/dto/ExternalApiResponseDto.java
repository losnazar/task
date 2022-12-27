package losnazar.task.model.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ExternalApiResponseDto {
    private BigDecimal lprice;
    private String curr1;
    private String curr2;
}
