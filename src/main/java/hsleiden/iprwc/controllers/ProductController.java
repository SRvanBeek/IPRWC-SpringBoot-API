package hsleiden.iprwc.controllers;

import hsleiden.iprwc.DAOs.ExtraImageDAO;
import hsleiden.iprwc.DAOs.ProductDAO;
import hsleiden.iprwc.Exceptions.NotFoundException;
import hsleiden.iprwc.entities.ApiResponse;
import hsleiden.iprwc.entities.ExtraImage;
import hsleiden.iprwc.entities.Product;
import hsleiden.iprwc.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {
    private final ProductDAO productDAO;
    private final ProductService productService;

    public ProductController(ProductDAO productDAO, ProductService productService) {
        this.productDAO = productDAO;
        this.productService = productService;
    }

    /**
     * returns all products in the database as an ArrayList if at least one product exists.
     * @return all products in an Arraylist
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResponse<ArrayList<Product>> getAll() {
        ArrayList<Product> products = productDAO.getAll();
        if (products.size() == 0) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND, null, "No products exist!");
        }

        return new ApiResponse<>(HttpStatus.ACCEPTED, products, "products successfully received!");
    }

    /**
     * returns an ArrayList of products by a given type if at least one product of the given type exists.
     * @param type the type of product to be received.
     * @return an ArrayList of products by a given type.
     */
    @RequestMapping(value = "/bytype/{type}", method = RequestMethod.GET)
    public ApiResponse<ArrayList<Product>> getAllByType(@PathVariable String type) {
        ArrayList<Product> products = productDAO.getAllByType(type);
        if (products.size() == 0) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND, null, "No products exist!");
        }
        System.out.println(products);

        return new ApiResponse<>(HttpStatus.ACCEPTED, products, "products successfully received!");
    }

    /**
     * returns a given amount of products.
     * @param amount the amount of random products to be received.
     * @return an ArrayList of products.
     */
    @RequestMapping(value = "/random/{amount}", method = RequestMethod.GET)
    public ApiResponse<ArrayList<Product>> getRandomProduct(@PathVariable int amount) {
        return new ApiResponse<>(HttpStatus.ACCEPTED, this.productService.randomProducts(amount), "random products successfully received!");
    }

    /**
     * gets a specific product if one exists with the given id.
     * @param id the product id.
     * @return a Product with the given id.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ApiResponse<Product> getProduct(@PathVariable long id) {
        Optional<Product> product = this.productDAO.getOneByID(id);
        if (product.isEmpty()) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND, null, "selected product does not exist!");
        }

        return new ApiResponse<>(HttpStatus.ACCEPTED, product.get(), "product successfully received");
    }

    /**
     * adds a new product to the database
     * @param product
     * @return an Apiresponse with the corresponding message
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ApiResponse<String> addProduct(@RequestBody Product product) {
        try {
            this.productDAO.addProduct(product);
        }
        catch (NotFoundException e) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND, e.getMessage(), product.getName() + " could not be added");
        }

        catch (Exception e) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN, e.getMessage(), product.getName() + " could not be added");
        }
        return new ApiResponse<>(HttpStatus.ACCEPTED, null, product.getName() + " was succesfully added!");
    }


}
