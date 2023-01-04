package hsleiden.iprwc.controllers;

import hsleiden.iprwc.DAOs.ProductDAO;
import hsleiden.iprwc.entities.ApiResponse;
import hsleiden.iprwc.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {
    private final ProductDAO productDAO;

    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    //get
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResponse<ArrayList<Product>> getAll() {
        ArrayList<Product> products = productDAO.getAll();
        if (products.size() == 0) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND, null, "No products exist!");
        }
        System.out.println(products);

        return new ApiResponse<>(HttpStatus.ACCEPTED, products, "products successfully received!");
    }




    //post
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ApiResponse<String> addProduct(@RequestBody Product product) {
        try {
            this.productDAO.addProduct(product);
        }
        catch (Exception e) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN, null, product.getName() + " could not be added");
        }
        return new ApiResponse<>(HttpStatus.ACCEPTED, null, product.getName() + " was succesfully added!");
    }
}
