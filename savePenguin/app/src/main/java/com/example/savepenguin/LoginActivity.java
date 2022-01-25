package com.example.savepenguin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

public class LoginActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private Button loginBtn, registerBtn;

    private RegisterFragment registerFragment;
    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerFragment = new RegisterFragment();
        loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,loginFragment).commit();


    }

    public void onChangeFragment(int index) {
        if (index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, loginFragment).commit();
        } else if (index == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, registerFragment).commit();
        }
    }


}