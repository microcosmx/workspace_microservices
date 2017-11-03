package consignprice.repository;

import consignprice.domain.PriceConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConsignPriceConfigRepository extends MongoRepository<PriceConfig, String> {
    @Query("{ 'id': ?0 }")
    PriceConfig findById(UUID id);

    PriceConfig find();
}
