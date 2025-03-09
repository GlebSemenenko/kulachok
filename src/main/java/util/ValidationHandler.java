package util;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
public class ValidationHandler {
    public Map<String, Object> handleValidationErrors(BindingResult bindingResult) {
        Map<String, Object> errorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return errorMap;
    }
}
