package hsleiden.iprwc.controllers;

import hsleiden.iprwc.DAOs.ExtraImageDAO;
import hsleiden.iprwc.entities.ApiResponse;
import hsleiden.iprwc.entities.ExtraImage;
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
}
