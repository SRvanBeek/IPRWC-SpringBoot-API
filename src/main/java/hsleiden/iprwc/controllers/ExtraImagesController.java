package hsleiden.iprwc.controllers;

import hsleiden.iprwc.DAOs.ExtraImageDAO;
import hsleiden.iprwc.entities.ApiResponse;
import hsleiden.iprwc.entities.ExtraImage;
import hsleiden.iprwc.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/productImages")
public class ExtraImagesController {

    private final ExtraImageDAO extraImageDAO;

    public ExtraImagesController(ExtraImageDAO extraImageDAO) {
        this.extraImageDAO = extraImageDAO;
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public ApiResponse<ArrayList<ExtraImage>> getExtraImages(@PathVariable long productId) {
        Optional<ArrayList<ExtraImage>> extraImages = this.extraImageDAO.getExtraImages(productId);
        if (extraImages.isEmpty()) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND, null, "product has no additional images!");
        }

        return new ApiResponse<>(HttpStatus.ACCEPTED, extraImages.get(), "extra images successfully received");
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ApiResponse<String> addImagesToProduct(@RequestBody ArrayList<ExtraImage> extraImages) {
        try {
            this.extraImageDAO.addImagesToProduct(extraImages);
            return new ApiResponse<>(HttpStatus.ACCEPTED, null, "images successfully added!");
        }
        catch (Exception e) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN, "", e.getMessage());
        }
    }

    @RequestMapping(value = "/one", method = RequestMethod.POST)
    public ApiResponse<String> addOneExtraImage(@RequestBody ExtraImage extraImage) {
        try {
            this.extraImageDAO.addOneImageToProduct(extraImage);
            return new ApiResponse<>(HttpStatus.ACCEPTED, null, "images successfully added!");
        }
        catch (Exception e) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN, "", e.getMessage());
        }
    }

    @RequestMapping(value = "/perProduct/{id}", method = RequestMethod.PUT)
    public ApiResponse<String> deleteImagesForProduct(@PathVariable long id) {
        try {
            this.extraImageDAO.disableImagesPerProduct(id);
            return new ApiResponse<>(HttpStatus.ACCEPTED, null, "All additional images deleted from product: " + id);
        }
        catch (Exception e) {
            return new ApiResponse<>(HttpStatus.ACCEPTED, e.getMessage(), "Something went wrong, no images have been deleted from product id: " + id);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ApiResponse<String> deleteOneExtraImage(@PathVariable long id) {
        try {
            this.extraImageDAO.deleteOneExtraImage(id);
            return new ApiResponse<>(HttpStatus.ACCEPTED, null, "image deleted successfully");
        }
        catch (Exception e) {
            return new ApiResponse<>(HttpStatus.ACCEPTED, e.getMessage(), "Something went wrong, image has not been deleted deleted");
        }
    }
}
