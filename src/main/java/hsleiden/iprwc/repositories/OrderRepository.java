package hsleiden.iprwc.repositories;

import hsleiden.iprwc.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
