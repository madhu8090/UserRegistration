package com.example.userregisteration.helper;

import android.text.TextUtils;
import android.util.Patterns;

public class EditTextUtils {

    public static boolean isInvalidEmail(CharSequence target) {
        return (TextUtils.isEmpty(target) || !Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isInvalidPhone(String target) {

        boolean valid = !TextUtils.isEmpty(target);

        if (valid) {
            valid = target.startsWith("+91") ? target.length() == 13 : target.length() == 10;
        }

        if (valid) {
            valid = TextUtils.isDigitsOnly(target.replace("+91", ""));
        }

        return !valid;
    }
}
