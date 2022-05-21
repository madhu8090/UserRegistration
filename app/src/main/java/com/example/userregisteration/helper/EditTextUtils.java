package com.example.userregisteration.helper;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

public class EditTextUtils {

    public static boolean isInvalidEmail(CharSequence target) {
        return (TextUtils.isEmpty(target) || !Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isInvalidName(CharSequence target) {
        return (TextUtils.isEmpty(target) || TextUtils.isDigitsOnly(target));
    }

    public static boolean isInvalidNumber(CharSequence target) {
        return (TextUtils.isEmpty(target) || !TextUtils.isDigitsOnly(target));
    }


    public static boolean isInvalidNumber(EditText target, String errorMsg) {
        boolean result = (TextUtils.isEmpty(target.getText().toString()) || !TextUtils.isDigitsOnly(target.getText().toString()));

        if (result) {
            target.setError(errorMsg);
        }

        return result;
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
