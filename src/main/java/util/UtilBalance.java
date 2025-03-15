package util;

import java.math.BigDecimal;

public class UtilBalance {

    private UtilBalance() {}

    public static void checkBalanceNonNegative(BigDecimal balance) {
        if (balance.signum() < 0) {
            throw new IllegalStateException("Insufficient funds: balance cannot be negative");
        }
    }
}
