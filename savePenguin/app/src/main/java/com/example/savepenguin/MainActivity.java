package com.example.savepenguin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.savepenguin.account.SharedPreference;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private PenguinFragement penguinFragement;

    private Button QR_menuBtn, sidebar_closeBtn;
    private TextView text_userId, sidebar_userId, sidebar_point, sidebar_myPage, sidebar_penguinShop;

    private String userID;
    private int userPoint;

    private DrawerLayout drawerLayout;
    private View drawerView;

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

        initSidebar();

    }

    public void initSidebar() {

        sidebar_userId = findViewById(R.id.sidebar_userid);
        sidebar_userId.setText(userID + "님");

        userPoint = 1000;
        sidebar_point = findViewById(R.id.sidebar_point);
        sidebar_point.setText("보유 포인트 : "+ String.valueOf(userPoint));

        sidebar_closeBtn = findViewById(R.id.sidebar_close);
        sidebar_closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("메인 페이지", "사이드바 닫음");
                drawerLayout.close();
            }
        });

        sidebar_myPage = findViewById(R.id.sidebar_mypage);
        sidebar_myPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("메인 페이지", "마이페이지 메뉴 버튼 누름");
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent);
            }
        });
        sidebar_penguinShop = findViewById(R.id.sidebar_penguinshop);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer2);

        drawerLayout.addDrawerListener(listener);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            //슬라이드 했을때
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
            //Drawer가 오픈된 상황일때 호출
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
            // 닫힌 상황일 때 호출
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            // 특정상태가 변결될 때 호출
        }
    };

    public void btnOnclick(View view) {
        switch (view.getId()){
            case R.id.button_opensidebar:
                Log.v("메인 페이지", "사이드바 열음");
                drawerLayout.openDrawer(drawerView);
        }
    }



}