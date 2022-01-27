package com.example.savepenguin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.savepenguin.account.SharedPreference;

public class MainActivity extends AppCompatActivity {

    private PenguinFragement penguinFragement;

    private Button QR_menuBtn;
    private TextView text_userId;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("메인 페이지", "메인 Activity 시작");
        penguinFragement = new PenguinFragement();
        userID = SharedPreference.getAttribute(getApplicationContext(), "userid");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView_main, penguinFragement).commit();

        text_userId = findViewById(R.id.text_userid);
        text_userId.setText("ID : "+userID);

        QR_menuBtn = findViewById(R.id.QR_MenuBtn);
        QR_menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("메인 페이지", "QR 메뉴 버튼 누름");
                Intent intent = new Intent(getApplicationContext(), QRManagementActivity.class);
                startActivity(intent);
            }
        });

    }
}