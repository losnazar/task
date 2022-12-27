package losnazar.task.service;

import java.io.File;
import java.util.List;
import losnazar.task.model.CurrencyPrice;
import org.springframework.data.domain.PageRequest;

public interface CurrencyPriceService {
    void fetchLastPrices();

    CurrencyPrice getMinPrice(String pair);

    CurrencyPrice getMaxPrice(String pair);

    List<CurrencyPrice> findAllByPair(String pair,
                                      PageRequest pageRequest);

    File getReport();
}
