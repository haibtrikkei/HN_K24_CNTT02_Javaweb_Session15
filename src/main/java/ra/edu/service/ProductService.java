package ra.edu.service;

import org.springframework.data.domain.Page;
import ra.edu.model.entity.Product;

public interface ProductService {
    Page<Product> getProductsByProducerAndPriceAndSorting(String producer, Double min, Double max, String sortBy, String orderBy, Integer page, Integer itemPerPage);
    void initDataForTest();
}

