package ra.edu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.edu.model.entity.Product;
import ra.edu.repository.ProductRepository;
import ra.edu.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> getProductsByProducerAndPriceAndSorting(String producer, Double min, Double max, String sortBy, String orderBy, Integer page, Integer itemPerPage) {
        Pageable pageable;
        Sort sort = null;

        //Khởi tạo đối tượng sắp xếp
        if(sortBy!=null){
            switch (sortBy){
                case "proId":
                    sort = Sort.by("proId");
                    break;
                case "proName":
                    sort = Sort.by("proName");
                    break;
                case "producer":
                    sort = Sort.by("producer");
                    break;
                case "yearMaking":
                    sort = Sort.by("yearMaking");
                    break;
                case "price":
                    sort = Sort.by("price");
                    break;
            }

            //xếp tăng hay giảm
            switch (orderBy){
                case "asc":
                    sort.ascending();
                    break;
                case "desc":
                    sort.descending();
                    break;
            }
        }

        //Khởi tạo pageable:
        if(sort!=null){
            pageable = PageRequest.of(page,itemPerPage,sort);
        }else{
            pageable = PageRequest.of(page,itemPerPage);
        }

        //xử lý producer ban đầu null:
        if(producer==null){
            producer = "";
        }

        //xử lý min và max ban đầu:
        if(min==null){
            min = 0D;
        }
        if(max==null){
            max = Double.MAX_VALUE;
        }
        return productRepository.findProductsByProducerContainingAndPriceBetween(producer,min,max,pageable);
    }

    @Override
    public void initDataForTest() {
        Random r = new Random();
        for(int i=1;i<=100;i++){
            Product product = new Product(null, "Sản phẩm dell " + i, "Dell", 2025, (double) r.nextInt(7000000, 30000000));
            productRepository.save(product);
        }

        for(int i=101;i<=200;i++){
            Product product = new Product(null, "Máy tính HP " + i, "HP", 2025, (double) r.nextInt(7000000, 30000000));
            productRepository.save(product);
        }
    }
}
