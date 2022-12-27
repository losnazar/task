package losnazar.task.controller;

import jakarta.validation.constraints.NotBlank;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import losnazar.task.lib.PairValid;
import losnazar.task.mapper.ResponseMapper;
import losnazar.task.model.CurrencyPrice;
import losnazar.task.model.dto.CurrencyPriceResponseDto;
import losnazar.task.service.CurrencyPriceService;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cryptocurrencies")
@Validated
public class CurrencyPriceController {
    private final ResponseMapper<CurrencyPriceResponseDto, CurrencyPrice> responseMapper;
    private final CurrencyPriceService currencyPriceService;

    public CurrencyPriceController(ResponseMapper<CurrencyPriceResponseDto,
            CurrencyPrice> responseMapper, CurrencyPriceService currencyPriceService) {
        this.responseMapper = responseMapper;
        this.currencyPriceService = currencyPriceService;
    }

    @GetMapping("/minprice")
    public CurrencyPriceResponseDto getMinPriceFor(@RequestParam
                                                       @PairValid
                                                       @NotBlank String name) {
        return responseMapper.toDto(currencyPriceService
                .getMinPrice(CurrencyPrice.Pair.valueOf(name).getValue()));
    }

    @GetMapping("/maxprice")
    public CurrencyPriceResponseDto getMaxPriceForPair(@RequestParam
                                                           @PairValid
                                                           @NotBlank String name) {
        return responseMapper.toDto(currencyPriceService
                .getMaxPrice(CurrencyPrice.Pair.valueOf(name).getValue()));
    }

    @GetMapping
    public List<CurrencyPriceResponseDto> getAllPricesForPair(@RequestParam
                                                                  @PairValid
                                                                  @NotBlank String name,
                                                         @RequestParam(defaultValue = "0")
                                                                 Integer page,
                                                         @RequestParam(defaultValue = "10")
                                                                          Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return currencyPriceService.findAllByPair(
                CurrencyPrice.Pair.valueOf(name).getValue(), pageRequest)
                .stream()
                .map(responseMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/csv")
    public String getCsvReport() {
        File report = currencyPriceService.getReport();
        return "Report is available by path: " + report.getAbsolutePath();
    }

    @GetMapping("/fetch")
    public String fetchData() {
        currencyPriceService.fetchLastPrices();
        return "Prices were fetched successfully!";
    }
}
