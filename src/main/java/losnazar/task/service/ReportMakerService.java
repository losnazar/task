package losnazar.task.service;

import java.util.Map;
import losnazar.task.model.CurrencyPrice;
import org.springframework.stereotype.Component;

@Component
public class ReportMakerService implements ReportMaker {
    private static final String PAIR = "Currency Pair";
    private static final String MIN_PRICE = "Min Price";
    private static final String MAX_PRICE = "Max Price";

    private final String header;

    public ReportMakerService() {
        this.header = String.join(",", PAIR, MIN_PRICE, MAX_PRICE);
    }

    @Override
    public String report(Map<CurrencyPrice.Pair, String> data) {
        String content = String.join(System.lineSeparator(), data.values());
        return String.join(System.lineSeparator(), header, content);
    }
}
