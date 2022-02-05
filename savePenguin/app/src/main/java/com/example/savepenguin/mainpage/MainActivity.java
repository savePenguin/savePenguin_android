package com.example.savepenguin.mainpage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.savepenguin.GetPointTask;
import com.example.savepenguin.IpSetting;
import com.example.savepenguin.R;
import com.example.savepenguin.account.LoginFragment;
import com.example.savepenguin.account.SharedPreference;
import com.example.savepenguin.mypage.MyPageActivity;
import com.example.savepenguin.mypage.PenguinShopActivity;
import com.example.savepenguin.qrpage.QRManagementActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    private PenguinFragement penguinFragement;

    private Button QR_menuBtn, sidebar_closeBtn;
    private TextView text_userId, sidebar_userId, sidebar_point, sidebar_myPage, sidebar_penguinShop, sidebar_logout;
    public TextView text_userPoint;

    IpSetting ipSetting = new IpSetting();
    private String userID;
    private int userPoint;

    private DrawerLayout drawerLayout;
    private View drawerView;
    private int REQUEST_PENGUIN_IMAGE = 0;
    public static Context context;
    public ImageView penguinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("메인 페이지", "메인 Activity 시작");
        penguinFragement = new PenguinFragement();
        userID = SharedPreference.getAttribute(getApplicationContext(), "userid");
        context = this;
        userPoint = 0;
        try {
            GetPointTask getPointTask = new GetPointTask();
            userPoint = Integer.parseInt(getPointTask.execute(userID).get());
            Log.v("메인 페이지", userID + "의 현재 포인트는 " + userPoint);

        } catch (Exception e) {

        }

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView_main, penguinFragement,"penguin").commit();
        penguinView = findViewById(R.id.imageView_penguin);
        text_userId = findViewById(R.id.text_userid);
        text_userId.setText("ID : " + userID);
        text_userPoint = findViewById(R.id.textView_userpoint_main);
        text_userPoint.setText(userPoint + "점");


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


    public void changePenguin(int imagecode) {
        System.out.println(imagecode);

    }

    public void initSidebar() {

        sidebar_userId = findViewById(R.id.sidebar_userid);
        sidebar_userId.setText(userID + "님");

        sidebar_point = findViewById(R.id.sidebar_point);
        sidebar_point.setText("보유 포인트 : " + String.valueOf(userPoint));

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
        sidebar_penguinShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("메인 페이지", "펭귄샵 메뉴 버튼 누름");
                Intent intent = new Intent(getApplicationContext(), PenguinShopActivity.class);
                startActivityForResult(intent, REQUEST_PENGUIN_IMAGE);
            }
        });

        sidebar_logout = findViewById(R.id.sidebar_logout);
        sidebar_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("메인 페이지", "로그아웃 버튼 누름");

                try {
                    LogoutTask task = new LogoutTask();
                    String result = task.execute(userID).get();
                    Log.v("메인 페이지", "통신 리턴값 : " + result);
                } catch (Exception e) {

                }

                //메인 액티비티 종료
                finish();
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawer2);

        drawerLayout.addDrawerListener(listener);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    class LogoutTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        String id;

        @Override
        // doInBackground의 매개변수 값이 여러개일 경우를 위해 배열로
        protected String doInBackground(String... strings) {
            try {
                id = strings[0];
                String str;
                URL url = new URL(ipSetting.getBaseUrl() + "/user/logout");  // 어떤 서버에 요청할지(localhost 안됨.)
                // ex) http://123.456.789.10:8080/hello/android
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                conn.setDoOutput(true);
                conn.setConnectTimeout(1000);

                // 서버에 보낼 값 포함해 요청함.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "userid=" + strings[0]; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                osw.flush();

                // jsp와 통신이 잘 되고, 서버에서 보낸 값 받음.
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                } else {    // 통신이 실패한 이유를 찍기위한 로그
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 서버에서 보낸 값을 리턴합니다.
            return receiveMsg;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            System.out.println(s);
        }
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
        switch (view.getId()) {
            case R.id.button_opensidebar:
                Log.v("메인 페이지", "사이드바 열음");
                drawerLayout.openDrawer(drawerView);
        }
    }


}