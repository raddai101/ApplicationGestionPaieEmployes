package com.gestionpaie.utils;

import java.util.regex.Pattern;

public class ValidationUtils {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+?[0-9]{7,15}$");

    public static boolean isEmailValid(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isPhoneValid(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
/*package com.gestionpaie.utils;

import java.util.regex.Pattern;

public class ValidationUtils {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+?[0-9]{7,15}$");

    public static boolean isEmailValid(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isPhoneValid(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
}*/
