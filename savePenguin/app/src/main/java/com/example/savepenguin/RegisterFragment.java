package com.example.savepenguin;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class RegisterFragment extends Fragment {

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
            }
        });

        return viewGroup;

    }

}