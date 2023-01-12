package hsleiden.iprwc.DAOs;

import hsleiden.iprwc.entities.ExtraImage;
import hsleiden.iprwc.repositories.ExtraImagesRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class ExtraImageDAO {
    private final ExtraImagesRepository extraImagesRepository;

    public ExtraImageDAO(ExtraImagesRepository extraImagesRepository) {
        this.extraImagesRepository = extraImagesRepository;
    }


    public Optional<ArrayList<ExtraImage>> getExtraImages(long id) {
        return extraImagesRepository.findAllByProduct(id);
    }

    public void addImagesToProduct(ArrayList<ExtraImage> extraImages) {
        this.extraImagesRepository.saveAll(extraImages);
    }
}
