package com.example.savepenguin.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.savepenguin.MainActivity;
import com.example.savepenguin.R;

import java.util.ArrayList;


public class LoginFragment extends Fragment {

    LoginActivity loginActivity;
    dummyData data = new dummyData();
    ArrayList<User> users = data.users;

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

                //입력 누락되었는지 확인 후 계정이 유효한지 확인
                if (isValidInput(id) && isValidInput(pw)) {
                    if (isAccountValid(data.users, id, pw)) {
                        Log.v("로그인 페이지", "로그인 버튼 성공");

                        Log.v("로그인 페이지", "로그인 정보 가져오기");
                        SharedPreference.setAttribute(getContext(), "userid", id);

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Log.v("로그인 페이지", "로그인 실패");
                        Toast.makeText(getActivity(),"아이디나 비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.v("로그인 페이지", "id, pw 입력 중 누락된 것이 존재");
                    Toast.makeText(getActivity(), "아이디나 비밀번호가 올바르게 입력되지 않았습니다", Toast.LENGTH_SHORT).show();
                }

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

    public static boolean isAccountValid(ArrayList<User> data, String id, String pw) {
        String userid, userpw;
        for (int i = 0; i < data.size(); i++) {
            userid = data.get(i).getUserid();
            userpw = data.get(i).getUserpw();
            if (userid.equals(id) && userpw.equals(pw)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidInput(String input) {
        if (input.equals("") | input == null) {
            return false;
        } else {
            return true;
        }
    }

}