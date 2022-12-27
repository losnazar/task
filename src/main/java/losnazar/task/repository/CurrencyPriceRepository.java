package losnazar.task.repository;

import java.util.List;
import losnazar.task.model.CurrencyPrice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyPriceRepository
        extends MongoRepository<CurrencyPrice, String> {
    CurrencyPrice findFirstByPairOrderByPriceAsc(String pair);

    CurrencyPrice findFirstByPairOrderByPriceDesc(String pair);

    List<CurrencyPrice> findAllByPairOrderByPrice(String pair,
                                                  Pageable pageable);
}
