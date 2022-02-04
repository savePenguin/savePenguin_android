package com.example.savepenguin.mainpage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.savepenguin.R;

public class PenguinFragement extends Fragment {

    MainActivity mainActivity;
    ViewGroup viewGroup;
    ImageView penguin;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.v("메인 페이지", "펭귄 Fragment 시작");
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        penguin = viewGroup.findViewById(R.id.imageView);

        return viewGroup;
    }

//    public void changePenguinImage(int imageCode) {
//        Log.v("메인 페이지", "펭귄 이미지 변경");
//        Drawable temp = getResources().getDrawable(imageCode);
//        penguin.setImageDrawable(temp);
//    }
}
