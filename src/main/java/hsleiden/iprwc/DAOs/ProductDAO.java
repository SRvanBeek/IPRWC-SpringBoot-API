package hsleiden.iprwc.DAOs;

import hsleiden.iprwc.Exceptions.NotFoundException;
import hsleiden.iprwc.entities.Product;
import hsleiden.iprwc.repositories.ProductRepository;
import hsleiden.iprwc.repositories.TypeRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class ProductDAO {
    private final ProductRepository productRepository;
    private final TypeRepository typeRepository;

    public ProductDAO(ProductRepository productRepository, TypeRepository typeRepository) {
        this.productRepository = productRepository;
        this.typeRepository = typeRepository;
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
        if (typeRepository.findByName(product.getType()).isEmpty()) {
            throw new NotFoundException("type " + product.getType() + " does not exist!");
        }

        this.productRepository.save(product);
    }

}
