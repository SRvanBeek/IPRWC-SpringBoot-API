package hsleiden.iprwc.repositories;

import hsleiden.iprwc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    ArrayList<Product> findAllByType(String type);

    ArrayList<Product> findAllById(long id);
}
