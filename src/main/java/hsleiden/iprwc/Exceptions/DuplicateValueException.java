package hsleiden.iprwc.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 409
public class DuplicateValueException extends RuntimeException{

    public DuplicateValueException(String message){
        super(message);
    }

}
