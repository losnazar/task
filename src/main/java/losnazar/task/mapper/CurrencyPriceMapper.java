package losnazar.task.mapper;

import java.time.LocalDateTime;
import losnazar.task.model.CurrencyPrice;
import losnazar.task.model.dto.CurrencyPriceResponseDto;
import losnazar.task.model.dto.ExternalApiResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CurrencyPriceMapper implements ResponseMapper<CurrencyPriceResponseDto, CurrencyPrice>,
        ExternalDataMapper {

    @Override
    public CurrencyPriceResponseDto toDto(CurrencyPrice model) {
        return new CurrencyPriceResponseDto(
                model.getId(),
                model.getPrice(),
                model.getPair(),
                model.getCreatedAt()
        );
    }

    @Override
    public CurrencyPrice toModel(ExternalApiResponseDto responseDto) {
        return new CurrencyPrice(
                responseDto.getLprice(),
                CurrencyPrice.Pair.valueOf(responseDto
                        .getCurr1()).getValue(),
                LocalDateTime.now()
        );
    }
}
