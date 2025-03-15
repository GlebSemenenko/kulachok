package util;

import com.kulachok.kulachok.entity.Actor;
import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.entity.model_interface.CashAccountHolder;
import com.kulachok.kulachok.repository.ActorRepository;
import com.kulachok.kulachok.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class UtilEntity {

    private static final Logger log = LoggerFactory.getLogger(UtilEntity.class);

    private UtilEntity (){}

    public static void checkFieldForNullOrEmpty(String value, String fieldName, Consumer<String> setter) {
        if (value != null && !value.isEmpty()) {
            setter.accept(value);
        } else {
            log.info("Field '{}' cannot be null or empty", fieldName);
        }
    }

    public static <T> void checkFieldForNull(T value, String fieldName, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        } else {
            log.info("Field '{}' cannot be null", fieldName);
        }
    }

    public static void checkFieldForNullOrEmptyException(String value, String fieldName, Consumer<String> setter) {
        if (value != null && !value.isEmpty()) {
            setter.accept(value);
        } else {
            throw new IllegalArgumentException("Field cannot be null or empty: " + fieldName);
        }
    }

    public static <T> void checkFieldForNullException(T value, String fieldName, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        } else {
            throw new IllegalArgumentException("Field cannot be null: " + fieldName);
        }
    }


    public static Class<? extends CashAccountHolder> getUserTypeAndCheckExistence(int userId,
                                                                                  String accountType,
                                                                                  UserRepository userRepository,
                                                                                  ActorRepository actorRepository) {
        boolean exists;

        Class<? extends CashAccountHolder> userType = switch (accountType.toLowerCase()) {
            case "user" -> {
                exists = userRepository.existsById(userId);
                yield User.class;
            }
            case "actor" -> {
                exists = actorRepository.existsById(userId);
                yield Actor.class;
            }
            default -> throw new IllegalArgumentException("Unknown account type: " + accountType);
        };

        log.info("Checking if {} exists with id {}: {}", accountType, userId, exists);
        if (!exists) {
            throw new NoSuchElementException(accountType + " with id " + userId + " does not exist");
        }

        return userType;
    }


}
