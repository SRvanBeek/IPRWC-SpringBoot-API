package hsleiden.iprwc.DAOs;

import hsleiden.iprwc.Exceptions.NotFoundException;
import hsleiden.iprwc.entities.ExtraImage;
import hsleiden.iprwc.entities.Product;
import hsleiden.iprwc.repositories.ExtraImagesRepository;
import hsleiden.iprwc.repositories.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class ExtraImageDAO {
    private final ExtraImagesRepository extraImagesRepository;
    private final ProductRepository productRepository;

    public ExtraImageDAO(ExtraImagesRepository extraImagesRepository, ProductRepository productRepository) {
        this.extraImagesRepository = extraImagesRepository;
        this.productRepository = productRepository;
    }


    public Optional<ArrayList<ExtraImage>> getExtraImages(long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return Optional.empty();
        }
        return extraImagesRepository.findAllByProductAndEnabled(product.get(), true);
    }

    public void addImagesToProduct(ArrayList<ExtraImage> extraImages) {
        this.extraImagesRepository.saveAll(extraImages);
    }

    public void addOneImageToProduct(ExtraImage extraImage) {
        this.extraImagesRepository.save(extraImage);
    }

    public void disableImagesPerProduct(long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new NotFoundException("Product does not exist!");
        }
        ArrayList<ExtraImage> extraImages = extraImagesRepository.findAllByProductAndEnabled(product.get(), true).get();

        for (ExtraImage extraImage: extraImages
             ) {
            extraImage.setEnabled(false);
            this.extraImagesRepository.save(extraImage);
        }
    }

    public void deleteOneExtraImage(long id) {
        this.extraImagesRepository.deleteById(id);
    }
}
