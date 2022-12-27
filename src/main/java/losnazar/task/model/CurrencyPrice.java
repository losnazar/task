package losnazar.task.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document("currency_prices")
public class CurrencyPrice {
    @Id
    private String id;
    private BigDecimal price;
    private String pair;
    private LocalDateTime createdAt;

    public CurrencyPrice(BigDecimal price,
                         String pair,
                         LocalDateTime createdAt) {
        this.price = price;
        this.pair = pair;
        this.createdAt = createdAt;
    }

    public enum Pair {
        BTC("BTC/USD"),
        ETH("ETH/USD"),
        XRP("XRP/USD");

        private final String value;

        Pair(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
