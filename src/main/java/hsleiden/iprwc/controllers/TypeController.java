package hsleiden.iprwc.controllers;

import hsleiden.iprwc.DAOs.TypeDAO;
import hsleiden.iprwc.entities.ApiResponse;
import hsleiden.iprwc.entities.Type;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
@RequestMapping(value = "/api/productTypes")
public class TypeController {
    private final TypeDAO typeDAO;

    public TypeController(TypeDAO typeDAO) {
        this.typeDAO = typeDAO;
    }

    /**
     * returns a list of all product types if at least one exists.
     * @return an ArrayList of types
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResponse<ArrayList<Type>> getAll() {
        ArrayList<Type> types = typeDAO.getAll();
        if (types.size() == 0) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND, null, "No types exist!");
        }

        return new ApiResponse<>(HttpStatus.ACCEPTED, types, "products successfully received!");
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ApiResponse<String> addType(@RequestBody Type type) {
        try {
            this.typeDAO.saveType(type);
            return new ApiResponse<>(HttpStatus.ACCEPTED, null, "Type "+ type.getName() + " successfully added!");
        }
        catch (Exception e) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN, e.getMessage(), "Type could not be added");
        }
    }
}
