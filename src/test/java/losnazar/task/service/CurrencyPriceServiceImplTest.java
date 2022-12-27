package losnazar.task.service;

import losnazar.task.model.CurrencyPrice;
import losnazar.task.model.dto.ExternalApiResponseDto;
import losnazar.task.repository.CurrencyPriceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
class CurrencyPriceServiceImplTest {
    private static final String CONTENT = "content";
    private static final String URL = "https://cex.io/api/last_price/BTC/USD";
    private static final String PATH = "src/main/resources/report.csv";

    @InjectMocks
    private CurrencyPriceServiceImpl currencyPriceService;
    @Mock
    private ReportMaker reportMaker;
    @Mock
    private CurrencyPriceRepository priceRepository;
    @Mock
    HttpClient httpClient;
    @Mock
    private CsvFileWriterService fileWriterService;

    @Test
    void fetchLastPrices_Ok() {
        ExternalApiResponseDto dto = new ExternalApiResponseDto();
        dto.setLprice(BigDecimal.valueOf(1200));
        dto.setCurr1(CurrencyPrice.Pair.BTC.name());
        dto.setCurr2("USD");

        Mockito.when(httpClient.get(URL, ExternalApiResponseDto.class))
                .thenReturn(dto);

        ExternalApiResponseDto actualDto = httpClient.get(URL, ExternalApiResponseDto.class);

        Assertions.assertEquals(actualDto.getLprice(), dto.getLprice());
        Assertions.assertEquals(actualDto.getCurr1(), dto.getCurr1());
        Assertions.assertEquals(actualDto.getCurr2(), dto.getCurr2());

        String id = "1231rcasc1";

        CurrencyPrice currencyPrice = new CurrencyPrice(
                id,
                BigDecimal.valueOf(1200),
                CurrencyPrice.Pair.BTC.name(),
                LocalDateTime.now()
        );

        Mockito.when(priceRepository.insert(currencyPrice))
                .thenReturn(currencyPrice);

        CurrencyPrice actual = priceRepository.insert(currencyPrice);
        Assertions.assertEquals(actual.getId(), currencyPrice.getId());
    }

    @Test
    void getReport_Ok() {
        String report = "Report";
        String pair = CurrencyPrice.Pair.ETH.getValue();
        HashMap<CurrencyPrice.Pair, String> data = new HashMap<>();

        CurrencyPrice minPrice = new CurrencyPrice(
                BigDecimal.valueOf(500),
                pair,
                LocalDateTime.now()
        );

        CurrencyPrice maxPrice = new CurrencyPrice(
                BigDecimal.valueOf(1000),
                pair,
                LocalDateTime.now()
        );

        Mockito.when(fileWriterService.writeToFile(CONTENT))
                .thenReturn(new File(PATH));

        File actualFile = fileWriterService.writeToFile(CONTENT);
        Assertions.assertEquals(actualFile.getClass(), File.class);

        Mockito.when(currencyPriceService.getMaxPrice(pair))
                .thenReturn(maxPrice);

        Mockito.when(currencyPriceService.getMinPrice(pair))
                .thenReturn(minPrice);

        CurrencyPrice actualMinPrice = currencyPriceService
                .getMinPrice(pair);
        Assertions.assertEquals(actualMinPrice.getPrice(),
                minPrice.getPrice());

        CurrencyPrice actualMaxPrice = currencyPriceService
                .getMaxPrice(pair);
        Assertions.assertEquals(actualMaxPrice.getPrice(),
                maxPrice.getPrice());

        Mockito.when(reportMaker.report(data))
                .thenReturn(report);

        String actualReport = reportMaker.report(data);
        Assertions.assertEquals(actualReport, report);
    }
}
