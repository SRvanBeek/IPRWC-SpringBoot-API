package hsleiden.iprwc.entities;

import org.springframework.http.HttpStatus;

public class ApiResponse<Type> {
    private HttpStatus code;
    private Type payload;
    private String message;

    public ApiResponse(HttpStatus code, Type payload, String message) {
        this.code = code;
        this.payload = payload;
        this.message = message;
    }

    public Type getPayload() {
        return payload;
    }

    public void setPayload(Type payload) {
        this.payload = payload;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
