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

public class Find_pwFragment extends Fragment {

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
        Log.v("로그인 페이지", "비밀번호 찾기 Fragment 시작");
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_findpw, container, false);

        TextView text_id = viewGroup.findViewById(R.id.editText_findpw_id);
        TextView text_name = viewGroup.findViewById(R.id.editText_findpw_name);
        TextView text_email = viewGroup.findViewById(R.id.editText_findpw_email);


        Button goBackBtn = viewGroup.findViewById(R.id.button_goback);
        goBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.v("회원가입 페이지", "뒤로가기 버튼 누름");
                loginActivity.onChangeFragment(0);
            }
        });

        Button submitBtn = viewGroup.findViewById(R.id.button_findpw_submit);
        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.v("비밀번호 찾기 페이지", "확인(비밀번호 찾기) 버튼 누름");
                String id, name, email;
                id = text_id.getText().toString();
                name = text_name.getText().toString();
                email = text_email.getText().toString();
                Log.v("비밀번호 찾기 페이지", "id : " + id +" name : " + name + " email : " + email);

                if (isValidInput(name) && isValidInput(email) && isValidInput(id)) {

                    //비밀번호 찾기

                } else {
                    Log.v("비밀번호 찾기 페이지", "id, name, email 입력 중 누락된 것이 존재");
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