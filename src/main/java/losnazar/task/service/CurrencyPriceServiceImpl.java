package losnazar.task.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import losnazar.task.mapper.ExternalDataMapper;
import losnazar.task.model.CurrencyPrice;
import losnazar.task.model.dto.ExternalApiResponseDto;
import losnazar.task.repository.CurrencyPriceRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CurrencyPriceServiceImpl implements CurrencyPriceService {
    private static final String URL_BTC_USD = "https://cex.io/api/last_price/BTC/USD";
    private static final String URL_ETH_USD = "https://cex.io/api/last_price/ETH/USD";
    private static final String URL_XRP_USD = "https://cex.io/api/last_price/XRP/USD";

    private final Map<CurrencyPrice.Pair, String> pairMap;
    private final ExternalDataMapper mapper;
    private final CurrencyPriceRepository repository;
    private final HttpClient httpClient;
    private final ReportMaker reportMaker;
    private final CsvFileWriter fileWriter;

    public CurrencyPriceServiceImpl(ExternalDataMapper mapper,
                                    CurrencyPriceRepository repository,
                                    HttpClient httpClient,
                                    ReportMaker reportMaker,
                                    CsvFileWriter fileWriter) {
        this.mapper = mapper;
        this.repository = repository;
        this.httpClient = httpClient;
        this.reportMaker = reportMaker;
        this.fileWriter = fileWriter;
        this.pairMap = new HashMap<>();

        pairMap.put(CurrencyPrice.Pair.BTC, URL_BTC_USD);
        pairMap.put(CurrencyPrice.Pair.ETH, URL_ETH_USD);
        pairMap.put(CurrencyPrice.Pair.XRP, URL_XRP_USD);
    }

    @Scheduled(cron = "*/30 * * * *")
    @Override
    public void fetchLastPrices() {
        pairMap.values().forEach(value -> {
            ExternalApiResponseDto dto = httpClient.get(value,
                    ExternalApiResponseDto.class);
            repository.insert(mapper.toModel(dto));
        });
    }

    @Override
    public CurrencyPrice getMinPrice(String pair) {
        return repository.findFirstByPairOrderByPriceAsc(pair);
    }

    @Override
    public CurrencyPrice getMaxPrice(String pair) {
        return repository.findFirstByPairOrderByPriceDesc(pair);
    }

    @Override
    public List<CurrencyPrice> findAllByPair(String pair, PageRequest pageRequest) {
        return repository.findAllByPairOrderByPrice(pair, pageRequest);
    }

    @Override
    public File getReport() {
        Map<CurrencyPrice.Pair, String> data = new HashMap<>();

        pairMap.keySet().forEach(pair -> {
            String maxPrice = getMaxPrice(pair.getValue()).getPrice().toString();
            String minPrice = getMinPrice(pair.getValue()).getPrice().toString();
            data.put(pair, String.join(",", pair.getValue(), minPrice, maxPrice));
        });

        String report = reportMaker.report(data);
        return fileWriter.writeToFile(report);
    }
}
