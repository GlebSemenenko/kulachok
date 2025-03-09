package util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

public class PasswordUtil {

    private PasswordUtil() {}

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String hashPassword(char[] password) {
        String passwordString = new String(password);
        String hashed = passwordEncoder.encode(passwordString);

        Arrays.fill(password, '\0');

        return hashed;
    }

    public static boolean checkPassword(char[] password, String storedHash) {
        String passwordString = new String(password);
        boolean matches = passwordEncoder.matches(passwordString, storedHash);

        Arrays.fill(password, '\0');

        return matches;
    }
}
