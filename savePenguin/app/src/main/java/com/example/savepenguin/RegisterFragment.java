package com.example.savepenguin;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_register, container, false);
        Button goBackBtn = viewGroup.findViewById(R.id.button_goback);
        goBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.v("회원가입 페이지", "뒤로가기 버튼 누름");
                loginActivity.onChangeFragment(0);
            }
        });

        return viewGroup;

    }

}