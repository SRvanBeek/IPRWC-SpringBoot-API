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

    public OrderDAO(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public List<Order> getAll() {
        return this.orderRepository.findAll();
    }

    public void saveOne(Order order) {
        this.orderRepository.save(order);
    }


}
