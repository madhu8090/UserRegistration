package com.example.userregisteration.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import com.example.userregisteration.R;
import com.example.userregisteration.helper.DataBaseHelper;
import com.example.userregisteration.helper.EditTextUtils;
import com.example.userregisteration.helper.PrefKeys;
import com.example.userregisteration.helper.Prefs;
import com.example.userregisteration.helper.ToastUtil;
import com.example.userregisteration.model.User;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText mEmail;
    TextInputEditText mPassword;
    Button mLogin;
    Button mSignUp;

    private DataBaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHelper = new DataBaseHelper(this);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.btn_login);
        mSignUp = findViewById(R.id.btn_signup);

        mLogin.setOnClickListener(view -> {
            if (isValidated()) {
                checkLoginCred();
            }
        });

        mSignUp.setOnClickListener(view -> {
            openSignupActivity();
        });
    }

    private void openSignupActivity() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void checkLoginCred() {
        if (databaseHelper.checkUser(mEmail.getText().toString().trim()
                , mPassword.getText().toString().trim())) {
            Prefs.setBoolean(PrefKeys.LOGIN_STATUS, true);
            Prefs.setString(PrefKeys.LOGIN_EMAIL, mEmail.getText().toString());
            Intent intent = new Intent(this, MainActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            ToastUtil.toastShort(this, "Invalid Email id or Password");
        }
    }

    private boolean isValidated() {
        boolean valid = true;
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if (EditTextUtils.isInvalidEmail(email)) {
            mEmail.setError("Please Enter Valid Email Id");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Please Enter Password");
            valid = false;
        } else {
            mPassword.setError(null);
        }

        return valid;
    }

}