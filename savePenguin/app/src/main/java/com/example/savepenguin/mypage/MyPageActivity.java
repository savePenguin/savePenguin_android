package com.example.savepenguin.mypage;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savepenguin.R;
import com.example.savepenguin.account.SharedPreference;

public class MyPageActivity extends AppCompatActivity {

    private TextView text_userID, text_userRank,text_userEmail;
    private String userID;
    private String tempEmail = "brian654321@naver.com", tempRank = "Basic";
    private MyPageFragment myPageFragment;
    private PointHistoryFragment pointHistoryFragment;
    private ChangeFragment changeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        Log.v("마이 페이지", "myPage Activity 시작");
        userID = SharedPreference.getAttribute(getApplicationContext(), "userid");

        myPageFragment = new MyPageFragment();
        pointHistoryFragment = new PointHistoryFragment();
        changeFragment = new ChangeFragment();

        text_userID = findViewById(R.id.textView_id_mypage);
        text_userID.setText(userID + "님, 안녕하세요");

        text_userEmail = findViewById(R.id.textView_email_mypage);
        text_userRank = findViewById(R.id.textView_rank_mypage);

        // 유저 정보 받아와서 email 보여준다
        text_userEmail.setText("Email : " + tempEmail);
        text_userRank.setText("등급 : " + tempRank);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView_mypage, myPageFragment).commit();

    }

    public void onChangeFragment(int index) {
        //마이페이지
        if (index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView_mypage, myPageFragment).commit();
        }
        //비밀번호 교체
        else if (index == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView_mypage, changeFragment).commit();
        }
        //포인트 내역
        else if (index == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView_mypage, pointHistoryFragment).commit();
        }

    }
}
