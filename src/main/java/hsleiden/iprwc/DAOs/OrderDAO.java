package hsleiden.iprwc.DAOs;

import hsleiden.iprwc.entities.ApiResponse;
import hsleiden.iprwc.entities.Order;
import hsleiden.iprwc.entities.Product;
import hsleiden.iprwc.repositories.OrderRepository;
import hsleiden.iprwc.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDAO {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderDAO(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }
    public List<Order> getAll() {
        return this.orderRepository.findAll();
    }

    public ApiResponse<Order> placeOrder(int customerId, List<Integer> productIds) {
        Order order = new Order();
        order.setCustomerId(customerId);

        ArrayList<Product> products = new ArrayList<>();
        for (Integer productId : productIds) {
            Optional<Product> product = this.productRepository.findById(Long.valueOf(productId));
            if (product.isEmpty()) {
                return new ApiResponse<>(HttpStatus.NOT_FOUND, null, "product " + productId + " does not exist, order has not been placed");
            }
            products.add(product.get());
        }

        order.setProducts(products);
        this.orderRepository.save(order);

        return new ApiResponse<>(HttpStatus.ACCEPTED, order, "order placed!");
    }
}
