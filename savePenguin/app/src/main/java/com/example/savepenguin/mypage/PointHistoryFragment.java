package com.example.savepenguin.mypage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.savepenguin.R;
import com.example.savepenguin.mypage.MyPageActivity;


public class PointHistoryFragment extends Fragment {

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

        Log.v("마이 페이지", "포인트 적립내역 Fragment 시작");
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_pointhistory, container, false);

        Button gobackBtn = viewGroup.findViewById(R.id.gobackBtn_pointHistory);

        gobackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPageActivity.onChangeFragment(0);
            }
        });

        return viewGroup;
    }
}
