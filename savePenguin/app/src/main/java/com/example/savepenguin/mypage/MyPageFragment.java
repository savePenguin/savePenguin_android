package com.example.savepenguin.mypage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.savepenguin.R;

public class MyPageFragment extends Fragment {
    MyPageActivity myPageActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myPageActivity = (MyPageActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        myPageActivity = null;
    }



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.v("마이 페이지", "MyPageFragment 시작");
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_mypage, container, false);


        TextView text_pointHistory = viewGroup.findViewById(R.id.text_beforepw);
        TextView text_changePW = viewGroup.findViewById(R.id.text_pwchange_mypage);
        TextView text_userDelete = viewGroup.findViewById(R.id.text_newpw);

        //포인트 내역 조회 누름
        text_pointHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("마이 페이지", "포인트 내역 조회 누름");
                myPageActivity.onChangeFragment(2);
            }
        });

        //비밀번호 변경 누름
        text_changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("마이 페이지", "비밀번호 변경 누름");
                myPageActivity.onChangeFragment(1);
            }
        });

        //회원 탈퇴 누름
        text_userDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("마이 페이지", "회원 탈퇴 누름");

                myPageActivity.finish();
            }
        });


        return viewGroup;
    }
}
