package hsleiden.iprwc.services;

import hsleiden.iprwc.DAOs.ProductDAO;
import hsleiden.iprwc.entities.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }


    public ArrayList<Product> randomProducts(int amount) {
        Random rand = new Random();
        ArrayList<Product> products = this.productDAO.getAll();
        ArrayList<Product> randomProducts = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            int selectedRandom = rand.nextInt(products.size());
            Product randomProduct = products.get(selectedRandom);
            products.remove(selectedRandom);
            randomProducts.add(randomProduct);
        }

        return randomProducts;
    }
}
