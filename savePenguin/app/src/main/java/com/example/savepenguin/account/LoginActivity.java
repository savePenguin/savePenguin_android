package com.example.savepenguin.account;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.savepenguin.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private Button loginBtn, registerBtn;

    private RegisterFragment registerFragment;
    private LoginFragment loginFragment;
    private Find_idFragment find_idFragment;
    private Find_pwFragment find_pwFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.v("로그인 페이지", "로그인 Activity 시작");
        registerFragment = new RegisterFragment();
        loginFragment = new LoginFragment();
        find_idFragment = new Find_idFragment();
        find_pwFragment = new Find_pwFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView_login,loginFragment).commit();

    }

    public void onChangeFragment(int index) {
        //로그인
        if (index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView_login, loginFragment).commit();
        }
        //회원가입
        else if (index == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView_login, registerFragment).commit();
        }
        //아이디 찾기
        else if (index == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView_login, find_idFragment).commit();
        }
        //비밀번호 찾기
        else if (index == 3) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView_login, find_pwFragment).commit();
        }
    }


}