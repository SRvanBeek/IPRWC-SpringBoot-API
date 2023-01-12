package hsleiden.iprwc.repositories;

import hsleiden.iprwc.entities.ExtraImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface ExtraImagesRepository extends JpaRepository<ExtraImage, Long> {
    public Optional<ArrayList<ExtraImage>> findAllByProduct(long productId);
}
