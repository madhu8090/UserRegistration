package com.example.userregisteration.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.userregisteration.R;
import com.example.userregisteration.helper.DataBaseHelper;
import com.example.userregisteration.helper.PrefKeys;
import com.example.userregisteration.helper.Prefs;
import com.example.userregisteration.helper.TableLayoutHelper;
import com.example.userregisteration.helper.Utils;
import com.example.userregisteration.model.User;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {

    TextView mUserName;
    TableLayout mUserDetailsTable;

    Button mUpdate;
    Button mLogOut;

    private DataBaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DataBaseHelper(this);
        String email = Prefs.getString(PrefKeys.LOGIN_EMAIL);
        User user = databaseHelper.getUserByEmail(email);

        mUserName = findViewById(R.id.user_name);
        mUserDetailsTable = findViewById(R.id.table_user_details);
        mUpdate = findViewById(R.id.btn_update);
        mLogOut = findViewById(R.id.btn_logout);

        if (!Prefs.getBoolean(PrefKeys.LOGIN_STATUS)) {
            openLoginPage();
        }

        mLogOut.setOnClickListener(view -> {
            Prefs.setBoolean(PrefKeys.LOGIN_STATUS, false);
            Prefs.setString(PrefKeys.LOGIN_EMAIL, null);
            openLoginPage();
        });

        mUpdate.setOnClickListener(view -> {
            Intent intent = new Intent(this, UpdateActivity.class);
            intent.putExtra("USER", user.toString());
            startActivity(intent);
        });

        mUserName.setText("Welcome " + user.getfName() + " " + user.getlName());
        showUserDetails(user);
    }

    private void openLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void showUserDetails(User user) {
        HashMap<String, String> details = new LinkedHashMap<>();
        details.put("Full Name", user.getfName() + " " + user.getlName());
        details.put("Date Of Birth", Utils.getAge(user.getDob()));
        details.put("Gender", user.getGender());
        details.put("Skills", user.getSkills());
        details.put("Email Id", user.getEmail());
        details.put("Mobile No", user.getMobile());
        TableLayoutHelper.buildTable(this, mUserDetailsTable, details);
    }
}