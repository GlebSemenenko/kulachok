package exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String s) {
        log.info("Resource not found: {}", s);
    }
}
