package data.sql.commons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Preconditions {

    public static <T> T checkNotNull(T value) {
        if (value == null) {
            throw new NullPointerException();
        }
        return value;
    }

    public static String checkNotEmpty(String value) {
        checkNotNull(value);
        if (value.length() == 0) {
            throw new NullPointerException("Value is empty");
        }
        return value;
    }

}
