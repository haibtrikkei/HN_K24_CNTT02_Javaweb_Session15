package ra.edu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.edu.model.dto.ProductDTO;
import ra.edu.model.dto.interface_based.ProductInterface;
import ra.edu.model.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findProductsByProducerContainingAndPriceBetween(String producer, Double min, Double max, Pageable pageable);

    @Query("select new ra.edu.model.dto.ProductDTO(p.proId,p.proName) from Product  p")
    List<ProductDTO> findOnlyIdAndNameOfProduct();

    List<ProductInterface> getProductWithIdAndName();
}
