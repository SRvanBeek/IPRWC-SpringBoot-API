package hsleiden.iprwc.controllers;

import hsleiden.iprwc.DAOs.OrderDAO;
import hsleiden.iprwc.entities.ApiResponse;
import hsleiden.iprwc.entities.Order;
import hsleiden.iprwc.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {
    private final OrderDAO orderDAO;
    private final OrderService orderService;
    public OrderController(OrderDAO orderDAO, OrderService orderService) {
        this.orderDAO = orderDAO;
        this.orderService = orderService;
    }


    @RequestMapping(path = "", method = RequestMethod.POST)
    public ApiResponse<Order> placeOrder(@RequestBody Map<String, Object> requestBody) {
        try {
            return this.orderService.placeOrder((Integer) requestBody.get("customerId"), (String) requestBody.get("cost"), (List<String>) requestBody.get("productIds"));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
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
