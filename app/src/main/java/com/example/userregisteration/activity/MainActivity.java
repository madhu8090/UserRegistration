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

        mUserName = findViewById(R.id.user_name);
        mUserDetailsTable = findViewById(R.id.table_user_details);
        mUpdate = findViewById(R.id.btn_update);
        mLogOut = findViewById(R.id.btn_logout);

        String email = Prefs.getString(PrefKeys.LOGIN_EMAIL);
        System.out.println("Email " + email);

        if (!Prefs.getBoolean(PrefKeys.LOGIN_STATUS)) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        mLogOut.setOnClickListener(view -> {
            Prefs.setBoolean(PrefKeys.LOGIN_STATUS, false);
            Prefs.setString(PrefKeys.LOGIN_EMAIL, null);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        User user = databaseHelper.getUserByEmail(email);
        mUserName.setText("Welcome " + user.getfName() + " " + user.getlName());
        showUserDetails(user);
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