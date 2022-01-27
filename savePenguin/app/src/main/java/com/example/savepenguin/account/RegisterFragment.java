package com.example.savepenguin.account;

import android.content.Context;
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

import com.example.savepenguin.R;
import com.example.savepenguin.account.LoginActivity;


public class RegisterFragment extends Fragment {

    LoginActivity loginActivity;
    dummyData data = new dummyData();

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
        Log.v("로그인 페이지", "회원가입 Fragment 시작");
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_register, container, false);

        TextView text_id = viewGroup.findViewById(R.id.editText_id);
        TextView text_pw = viewGroup.findViewById(R.id.editText_pw);
        TextView text_name = viewGroup.findViewById(R.id.editText_name);
        TextView text_email = viewGroup.findViewById(R.id.editText_email);
        TextView text_validNumber = viewGroup.findViewById(R.id.editText_validNumber);


        Button goBackBtn = viewGroup.findViewById(R.id.button_goback);
        goBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.v("회원가입 페이지", "뒤로가기 버튼 누름");
                loginActivity.onChangeFragment(0);
            }
        });

        Button registerBtn = viewGroup.findViewById(R.id.button_submit);
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.v("회원가입 페이지", "확인(회원가입) 버튼 누름");
                String id, pw, name, email;
                id = text_id.getText().toString();
                pw = text_pw.getText().toString();
                name = text_name.getText().toString();
                email = text_email.getText().toString();
                Log.v("회원가입 페이지", "id : " + id + " pw : " + pw + " name : " + name + " email : " + email);

                if (isValidInput(id) && isValidInput(pw) && isValidInput(name) && isValidInput(email)) {

                    // 입력 유효성만 검사한 후 서버에 회원 정보 보냄
                    // 서버에서 중복성 검사 후 결과 리턴
                    data.users.add(new User(id, pw, email, name, 0));
                    Log.v("회원가입 페이지", "회원가입 성공");
                    loginActivity.onChangeFragment(0);
                } else {
                    Log.v("회원가입 페이지", "입력 누락 ");
                    Toast.makeText(getActivity(), "회원 정보 입력이 누락되었습니다", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return viewGroup;

    }

    public static boolean isValidInput(String input) {
        if (input.equals("") | input == null) {
            return false;
        } else {
            return true;
        }
    }

}