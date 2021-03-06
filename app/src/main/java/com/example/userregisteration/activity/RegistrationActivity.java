package com.example.userregisteration.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.userregisteration.R;
import com.example.userregisteration.helper.DataBaseHelper;
import com.example.userregisteration.helper.EditTextUtils;
import com.example.userregisteration.helper.ToastUtil;
import com.example.userregisteration.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity {

    final Calendar calendar = Calendar.getInstance();

    TextInputEditText mFirstName;
    TextInputEditText mLastName;
    TextInputEditText mDob;
    AutoCompleteTextView mGender;
    TextInputEditText mSkills;
    TextInputEditText mEmail;
    TextInputEditText mPassword;
    TextInputEditText mConfirmPassword;
    TextInputEditText mMobile;

    Button mSignUp;

    boolean[] selectedSkills;
    private DataBaseHelper databaseHelper;
    ArrayList<Integer> skillsList = new ArrayList<>();
    String[] skills = {"Flutter", "Kotlin", "Java", "Android SDK", "Swift", "GIT", "NodeJs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        databaseHelper = new DataBaseHelper(this);

        mFirstName = findViewById(R.id.fname);
        mLastName = findViewById(R.id.lname);
        mDob = findViewById(R.id.dob);
        mGender = findViewById(R.id.gender);
        mSkills = findViewById(R.id.skills);
        mEmail = findViewById(R.id.email);
        mConfirmPassword = findViewById(R.id.confirm_password);
        mPassword = findViewById(R.id.password);
        mMobile = findViewById(R.id.mobile);

        mSignUp = findViewById(R.id.btn_signup);

        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            showDob();
        };

        selectedSkills = new boolean[skills.length];

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.gender));
        mGender.setAdapter(arrayAdapter);

        mDob.setOnClickListener(view -> new DatePickerDialog(this, date, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show());

        mSkills.setOnClickListener(view -> {
            showSkillsDialog(selectedSkills, skills);
        });

        mSignUp.setOnClickListener(view -> {
            if (isValidated()) {
                saveToDb(getUserDetails(new User()));
            }
        });
    }

    private void showSkillsDialog(boolean[] selectedSkills, String[] skills) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set title
        builder.setTitle("Select Skills");

        // set dialog non cancelable
        builder.setCancelable(false);

        builder.setMultiChoiceItems(skills, selectedSkills, (dialogInterface, i, b) -> {
            if (b) {
                skillsList.add(i);
                Collections.sort(skillsList);
            } else {
                skillsList.remove(Integer.valueOf(i));
            }
        });

        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            // Initialize string builder
            StringBuilder stringBuilder = new StringBuilder();
            // use for loop
            for (int j = 0; j < skillsList.size(); j++) {
                // concat array value
                stringBuilder.append(skills[skillsList.get(j)]);
                // check condition
                if (j != skillsList.size() - 1) {
                    // When j value  not equal
                    // to lang list size - 1
                    // add comma
                    stringBuilder.append(", ");
                }
            }
            // set text on textView
            mSkills.setText(stringBuilder.toString());
        });

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            // dismiss dialog
            dialogInterface.dismiss();
        });
        builder.setNeutralButton("Clear All", (dialogInterface, i) -> {
            // use for loop
            for (int j = 0; j < selectedSkills.length; j++) {
                // remove all selection
                selectedSkills[j] = false;
                // clear language list
                skillsList.clear();
                // clear text view value
                mSkills.setText("");
            }
        });
        // show dialog
        builder.show();
    }

    private void saveToDb(User user) {
        if (!databaseHelper.checkUser(mEmail.getText().toString())) {
            databaseHelper.addUser(user);
            ToastUtil.toastShort(this, "User Registered Successfully, Please login to continue");
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            ToastUtil.toastShort(this, "User Already Registered With This Email Id");
        }
    }

    private User getUserDetails(User user) {

        String fName = mFirstName.getText().toString();
        String lName = mLastName.getText().toString();
        String gender = mGender.getText().toString();
        String dob = mDob.getText().toString();
        String skills = mSkills.getText().toString();
        String email = mEmail.getText().toString();
        String mobile = mMobile.getText().toString();
        String password = mPassword.getText().toString();

        user.setfName(fName);
        user.setlName(lName);
        user.setMobile(mobile);
        user.setDob(dob);
        user.setSkills(skills);
        user.setEmail(email);
        user.setPassword(password);
        user.setGender(gender);

        return user;
    }

    private boolean isValidated() {
        boolean valid = true;
        String fName = mFirstName.getText().toString();
        String lName = mLastName.getText().toString();
        String gender = mGender.getText().toString();
        String dob = mDob.getText().toString();
        String skills = mSkills.getText().toString();
        String email = mEmail.getText().toString();
        String mobile = mMobile.getText().toString();
        String password = mPassword.getText().toString();
        String confirmPassword = mConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(fName)) {
            mFirstName.setError("Please Enter First Name");
            valid = false;
        } else {
            mFirstName.setError(null);
        }

        if (TextUtils.isEmpty(lName)) {
            mLastName.setError("Please Enter Last Name");
            valid = false;
        } else {
            mLastName.setError(null);
        }

        if (EditTextUtils.isInvalidPhone(mobile)) {
            mMobile.setError("Please Enter Valid Mobile Number");
            valid = false;
        } else {
            mMobile.setError(null);
        }

        if (EditTextUtils.isInvalidEmail(email)) {
            mEmail.setError("Please Enter Valid Email Id");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        if (TextUtils.isEmpty(dob)) {
            mDob.setError("Please Enter Date of Birth");
            valid = false;
        } else {
            mDob.setError(null);
        }

        if (TextUtils.isEmpty(gender)) {
            mGender.setError("Please Enter Your Gender");
            valid = false;
        } else {
            mGender.setError(null);
        }

        if (TextUtils.isEmpty(skills)) {
            mSkills.setError("Please Add your Skills");
            valid = false;
        } else {
            mSkills.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Please Enter Password");
            valid = false;
        } else {
            mPassword.setError(null);
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            mConfirmPassword.setError("Please Enter Confirmation Password");
            valid = false;
        } else {
            mConfirmPassword.setError(null);
        }
        if (!password.equals(confirmPassword)) {
            mConfirmPassword.setError("Password do not match");
            valid = false;
        } else {
            mConfirmPassword.setError(null);
        }

        return valid;
    }

    private void showDob() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        mDob.setText(dateFormat.format(calendar.getTime()));
    }
}