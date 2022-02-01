package com.example.savepenguin.mypage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savepenguin.Point;
import com.example.savepenguin.QR;
import com.example.savepenguin.R;

import java.util.ArrayList;


public class PointHistoryFragment extends Fragment {

    MyPageActivity myPageActivity;
    RecyclerView pointList;
    LinearLayoutManager linearLayoutManager;
    PointListAdapter adapter;
    ArrayList<Point> items = new ArrayList<>();

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
        pointList = viewGroup.findViewById(R.id.ListView_QRList);


        for (int i = 1; i < 6; i++) {
            items.add(new Point(10 * i, "2022.01.01", "스타벅스 여의도역점", "내 컵"));
        }

        linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        adapter = new PointListAdapter(items);
        pointList.setLayoutManager(linearLayoutManager);
        pointList.setAdapter(adapter);
        return viewGroup;
    }
}

class PointListAdapter extends RecyclerView.Adapter<PointListAdapter.Holder> {

    ArrayList<Point> items = new ArrayList<>();

    public PointListAdapter(ArrayList<Point> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PointListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_pointhistory_item, parent, false);
        return new PointListAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PointListAdapter.Holder holder, int position) {
        Point item = items.get(position);
        holder.pointName.setText("사용 텀블러 : " + item.getTumbler() + "        +" + item.getPoint() + "점");
        holder.aboutPoint.setText("획득 날짜"+item.getPointDate()+" 획득 위치 : "+item.getPointFrom());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView pointName;
        private TextView aboutPoint;
        public Holder(@NonNull View itemView) {
            super(itemView);
            pointName = itemView.findViewById(R.id.text_point_item);
            aboutPoint = itemView.findViewById(R.id.text_aboutpoint);
        }
    }
}
