package losnazar.task.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import losnazar.task.model.CurrencyPrice;
import losnazar.task.service.CurrencyPriceService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CurrencyPriceControllerTest {
    @MockBean
    private CurrencyPriceService currencyPriceService;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldReturnCurrencyPriceWithMinPrice_Ok() {
        String name = CurrencyPrice.Pair.ETH.name();
        String id = "63aa190bae7e126c8ec5802c";
        LocalDateTime time = LocalDateTime.now();

        CurrencyPrice currencyPrice = new CurrencyPrice(
                id,
                BigDecimal.valueOf(1234.5),
                CurrencyPrice.Pair.ETH.getValue(),
                time);

        Mockito.when(currencyPriceService.getMinPrice(CurrencyPrice.Pair.ETH.getValue()))
                .thenReturn(currencyPrice);

        RestAssuredMockMvc.given()
                .queryParam("name", name)
                .when()
                .get("/cryptocurrencies/minprice")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(4))
                .body("id", Matchers.equalTo(id));
    }

    @Test
    void shouldReturnCurrencyPriceWithMaxPrice_Ok() {
        String name = CurrencyPrice.Pair.BTC.name();
        String id = "63aa190bae7e126c8ec58012sa";
        LocalDateTime time = LocalDateTime.now();

        CurrencyPrice currencyPrice = new CurrencyPrice(id,
                BigDecimal.valueOf(16000),
                CurrencyPrice.Pair.BTC.getValue(),
                time);

        Mockito.when(currencyPriceService.getMaxPrice(CurrencyPrice.Pair.BTC.getValue()))
                .thenReturn(currencyPrice);

        RestAssuredMockMvc.given()
                .queryParam("name", name)
                .when()
                .get("/cryptocurrencies/maxprice")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(4))
                .body("id", Matchers.equalTo(id));
    }

    @Test
    void shouldReturnAllCurrencyPricesByPair_Ok() {
        String name = CurrencyPrice.Pair.XRP.name();
        LocalDateTime time = LocalDateTime.now();

        List<CurrencyPrice> mockPrices = List.of(
                new CurrencyPrice("63bae12e126c8ec58012sa",
                        BigDecimal.valueOf(1000),
                        CurrencyPrice.Pair.XRP.getValue(),
                        time),
                new CurrencyPrice("63bae12e126c8ec58012s1",
                        BigDecimal.valueOf(1010),
                        CurrencyPrice.Pair.XRP.getValue(),
                        time),
                new CurrencyPrice("63bae12e126c8ec5801212",
                        BigDecimal.valueOf(1200),
                        CurrencyPrice.Pair.XRP.getValue(),
                        time)
        );

        Mockito.when(currencyPriceService.findAllByPair(name,
                        PageRequest.of(0, 3)))
                .thenReturn(mockPrices);

        RestAssuredMockMvc.given()
                .queryParam("name", name)
                .queryParam("page", 0)
                .queryParam("size", 3)
                .when()
                .get("/cryptocurrencies")
                .then()
                .statusCode(200);
    }

    @Test
    void shouldReturnStatus_Ok() {
        File file = new File("src/main/resources/report.csv");

        Mockito.when(currencyPriceService.getReport())
                .thenReturn(file);

        RestAssuredMockMvc.when()
                .get("/cryptocurrencies/csv")
                .then()
                .statusCode(200);
    }
}