package hsleiden.iprwc.DAOs;

import hsleiden.iprwc.entities.Product;
import hsleiden.iprwc.repositories.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class ProductDAO {
    private final ProductRepository productRepository;

    public ProductDAO(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //get
    public ArrayList<Product> getAll() {
        return new ArrayList<>(this.productRepository.findAll());
    }

    public Optional<Product> getOneByID(long id) {
        return this.productRepository.findById(id);
    }

    //post
    public void addProduct(Product product) {
        this.productRepository.save(product);
    }
}
