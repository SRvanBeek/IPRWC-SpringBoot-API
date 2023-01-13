package hsleiden.iprwc.DAOs;

import hsleiden.iprwc.Exceptions.DuplicateValueException;
import hsleiden.iprwc.Exceptions.NotFoundException;
import hsleiden.iprwc.entities.Type;
import hsleiden.iprwc.repositories.TypeRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

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
        type.setName(type.getName().toUpperCase());
        if (typeRepository.findByName(type.getName()).isPresent()) {
            throw new DuplicateValueException("Type " + type.getName() + " already exists!");
        }

        this.typeRepository.save(type);
    }

    public void updateType(Type type) {
        type.setName(type.getName().toUpperCase());
        if (typeRepository.findById(type.getId()).isEmpty()) {
            throw new NotFoundException("can't update a type which does not exist!");
        }

        this.typeRepository.save(type);
    }

    public Optional<Type> findOneById(long id) {
        return this.typeRepository.findById(id);
    }


}
