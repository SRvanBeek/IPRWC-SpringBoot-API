package hsleiden.iprwc.controllers;

import hsleiden.iprwc.DAOs.OrderDAO;
import hsleiden.iprwc.entities.ApiResponse;
import hsleiden.iprwc.entities.Order;
import hsleiden.iprwc.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {
    private final OrderDAO orderDAO;

    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }


    @RequestMapping(path = "", method = RequestMethod.POST)
    public ApiResponse<Order> placeOrder(@RequestBody Map<String, Object> requestBody) {
        try {
            return this.orderDAO.placeOrder((Integer) requestBody.get("customerId"), (List<Integer>) requestBody.get("productIds"));
        }
        catch (Exception e) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ApiResponse<List<Order>> getAllOrders() {
        try {

            return new ApiResponse<>(HttpStatus.ACCEPTED, this.orderDAO.getAll(), "Orders received!");
        }
        catch (Exception e) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST, null, "Order could not be placed");
        }
    }
}
