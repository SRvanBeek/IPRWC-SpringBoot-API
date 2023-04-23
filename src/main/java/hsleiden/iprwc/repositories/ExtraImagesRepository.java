package hsleiden.iprwc.repositories;

import hsleiden.iprwc.entities.ExtraImage;
import hsleiden.iprwc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface ExtraImagesRepository extends JpaRepository<ExtraImage, Long> {
    Optional<ArrayList<ExtraImage>> findAllByProductAndEnabled(Product product, boolean enabled);
    void deleteAllByProduct(Product product);

}
