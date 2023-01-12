package hsleiden.iprwc.DAOs;

import hsleiden.iprwc.entities.ExtraImage;
import hsleiden.iprwc.entities.Product;
import hsleiden.iprwc.repositories.ExtraImagesRepository;
import hsleiden.iprwc.repositories.ProductRepository;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;
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

    public ArrayList<Product> getAllByType(String type) {
        return new ArrayList<>(this.productRepository.findAllByType(type));
    }

    public Optional<Product> getOneByID(long id) {
        return this.productRepository.findById(id);
    }

    //post
    public void addProduct(Product product) {
        this.productRepository.save(product);
    }

}
