package com.example.savepenguin;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savepenguin.account.SharedPreference;

public class MyPageActivity extends AppCompatActivity {

    private TextView text_userID, userPW;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        userID = SharedPreference.getAttribute(getApplicationContext(), "userid");

        text_userID = findViewById(R.id.text_id_mypage);
        text_userID.setText(userID);



    }

}
