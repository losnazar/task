package losnazar.task.mapper;

import losnazar.task.model.CurrencyPrice;
import losnazar.task.model.dto.ExternalApiResponseDto;

public interface ExternalDataMapper {
    CurrencyPrice toModel(ExternalApiResponseDto responseDto);
}
