package hsleiden.iprwc.DAOs;

import hsleiden.iprwc.entities.Type;
import hsleiden.iprwc.repositories.TypeRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TypeDAO {
    private final TypeRepository typeRepository;

    public TypeDAO(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public ArrayList<Type> getAll() {
        return (ArrayList<Type>) this.typeRepository.findAll();
    }

    public void saveType(Type type) {
        this.typeRepository.save(type);
    }


}
