package hsleiden.iprwc.services;

import hsleiden.iprwc.DAOs.OrderDAO;
import hsleiden.iprwc.DAOs.ProductDAO;
import hsleiden.iprwc.entities.ApiResponse;
import hsleiden.iprwc.entities.Order;
import hsleiden.iprwc.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;

    public OrderService(ProductDAO productDAO, OrderDAO orderDAO) {
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
    }

    public ApiResponse<Order> placeOrder(int customerId, double cost, List<String> productIdsStrings) {
        ArrayList<Integer> productIds = new ArrayList<>();
        try {
            for (int i = 0; i < productIdsStrings.size(); i++) {
                productIds.add(Integer.valueOf(productIdsStrings.get(i)));
            }
        }
        catch (NumberFormatException e) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST, null, "productId must be of a Integer type!");
        }
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setCost(cost);
        order.setOrderDate(ZonedDateTime.now());

        ArrayList<Product> products = new ArrayList<>();
        for (Integer productId : productIds) {
            Optional<Product> product = this.productDAO.getOneByID(Long.valueOf(productId));
            if (product.isEmpty()) {
                return new ApiResponse<>(HttpStatus.NOT_FOUND, null, "product " + productId + " does not exist, order has not been placed");
            }
            products.add(product.get());
        }

        order.setProducts(products);
        this.orderDAO.saveOne(order);

        return new ApiResponse<>(HttpStatus.ACCEPTED, order, "order placed!");
    }
}
