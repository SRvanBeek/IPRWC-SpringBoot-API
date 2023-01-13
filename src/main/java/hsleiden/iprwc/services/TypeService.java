package hsleiden.iprwc.services;

import hsleiden.iprwc.DAOs.ProductDAO;
import hsleiden.iprwc.DAOs.TypeDAO;
import hsleiden.iprwc.Exceptions.NotFoundException;
import hsleiden.iprwc.entities.Product;
import hsleiden.iprwc.entities.Type;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TypeService {
    private final TypeDAO typeDAO;
    private final ProductDAO productDAO;

    public TypeService(TypeDAO typeDAO, ProductDAO productDAO) {
        this.typeDAO = typeDAO;
        this.productDAO = productDAO;
    }


    public void updateType(Type type) {
        if (typeDAO.findOneById(type.getId()).isEmpty()) {
            throw new NotFoundException("cannot update type that does not exist!");
        }
        else {
            typeDAO.saveType(type);
        }
    }
}
