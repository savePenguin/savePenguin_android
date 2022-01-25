package com.example.savepenguin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class LoginFragment extends Fragment {

    LoginActivity loginActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        loginActivity = (LoginActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loginActivity = null;
    }



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        Log.v("로그인 페이지", "로그인 Fragment 시작");
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);

        TextView text_id = viewGroup.findViewById(R.id.textbox_userId);
        TextView text_pw = viewGroup.findViewById(R.id.textbox_userPw);


        Button loginBtn = viewGroup.findViewById(R.id.button_login);
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 로그인 버튼 눌러서 메인 페이지로 진입
                // 로그인 버튼을 눌렀을 때 그 정보가 유효함을 검사 해야함
                Log.v("로그인 페이지", "로그인 버튼 누름");
                String id, pw;
                id = text_id.getText().toString();
                pw = text_pw.getText().toString();
                Log.v("로그인 페이지", "id : " + id + " pw : " + pw);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button registerBtn = viewGroup.findViewById(R.id.button_register);
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.v("로그인 페이지", "회원가입 버튼 누름");
                loginActivity.onChangeFragment(1);
            }
        });

        TextView findId = viewGroup.findViewById(R.id.textView_findId);
        findId.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.v("로그인 페이지", "아이디찾기 버튼 누름");
                loginActivity.onChangeFragment(2);
            }
        });

        TextView findPw = viewGroup.findViewById(R.id.textView_findPw);
        findPw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.v("로그인 페이지", "비밀번호찾기 버튼 누름");
                loginActivity.onChangeFragment(3);
            }
        });

        return viewGroup;

    }

}