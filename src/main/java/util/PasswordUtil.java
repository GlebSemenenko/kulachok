package util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

public class PasswordUtil {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Метод для создания хеша пароля
    public static String hashPassword(char[] password) {
        String passwordString = new String(password);
        String hashed = passwordEncoder.encode(passwordString);

        // Очищаем пароль из памяти
        Arrays.fill(password, '\0');

        return hashed;
    }

    // Метод для проверки пароля
    public static boolean checkPassword(char[] password, String storedHash) {
        String passwordString = new String(password);
        boolean matches = passwordEncoder.matches(passwordString, storedHash);

        // Очищаем пароль из памяти
        Arrays.fill(password, '\0');

        return matches;
    }
}
