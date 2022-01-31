package com.example.savepenguin.mypage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.savepenguin.R;

public class ChangeFragment extends Fragment {

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
        Log.v("마이 페이지", "Change Fragment 시작");
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_changepw, container, false);

        EditText beforePw = viewGroup.findViewById(R.id.editText_beforepw);
        EditText newPw = viewGroup.findViewById(R.id.editText_newpw);
        EditText newPwVerify = viewGroup.findViewById(R.id.editText_newpw2);

        Button gobackBtn = viewGroup.findViewById(R.id.gobackBtn_changPw);

        gobackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPageActivity.onChangeFragment(0);
            }
        });

        return viewGroup;
    }
}
