package broker.repositories;

import broker.models.stocks.Stock;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "stocks", path = "stocks")
public interface StockRepository extends MongoRepository<Stock, String> {
  List<Stock> findBySymbol(@Param("symbol") String symbol);
}
