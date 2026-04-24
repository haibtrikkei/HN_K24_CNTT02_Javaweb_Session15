package ra.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ra.edu.model.entity.Product;
import ra.edu.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public String home(@RequestParam(name = "producer",required = false)String producer,
                       @RequestParam(name = "minPrice",required = false)Double minPrice,
                       @RequestParam(name = "maxPrice",required = false)Double maxPrice,
                       @RequestParam(name = "sortBy",defaultValue = "proId")String sortBy,
                       @RequestParam(name = "orderBy",defaultValue = "asc")String orderBy,
                       @RequestParam(name = "page",defaultValue = "1")Integer page,
                       Model model){
        Integer itemPerPage = 10; //Thay đổi để hiển thị số dữ liệu trên 1 trang
        Page<Product> productPage = productService.getProductsByProducerAndPriceAndSorting(producer, minPrice, maxPrice, sortBy, orderBy, page-1, itemPerPage);
        model.addAttribute("listProducts",productPage.getContent());
        model.addAttribute("page",page);
        List<Integer> listPages = new ArrayList<>();
        for(int i=1;i<=productPage.getTotalPages();i++){
            listPages.add(i);
        }
        model.addAttribute("totalPages",listPages);
        model.addAttribute("totalElements",productPage.getTotalElements());
        model.addAttribute("producer",producer);
        model.addAttribute("sortBy",sortBy);
        model.addAttribute("orderBy",orderBy);
        model.addAttribute("minPrice",minPrice);
        model.addAttribute("maxPrice",maxPrice);
        return "listProduct";
    }

    @GetMapping("/initData")
    public String initData(){
        productService.initDataForTest();
        return "redirect:/products";
    }
}
