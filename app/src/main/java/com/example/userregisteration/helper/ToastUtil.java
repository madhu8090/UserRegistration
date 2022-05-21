package com.example.userregisteration.helper;


import android.content.Context;
import android.widget.Toast;


public class ToastUtil {

    private static Toast toast = null;
    public static final String TAG = "TAG";

    public static void toastShort(Context context, Object message) {
        if (toast != null) {
            toast.cancel();
        }

        toast = Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void show(Context context, Object message) {
        if (toast != null) {
            toast.cancel();
        }

        toast = Toast.makeText(context, message.toString(), Toast.LENGTH_LONG);
        toast.show();
    }
}
