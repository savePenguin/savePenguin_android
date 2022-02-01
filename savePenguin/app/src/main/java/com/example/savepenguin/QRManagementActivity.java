package com.example.savepenguin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QRManagementActivity extends AppCompatActivity {

    RecyclerView userList;
    LinearLayoutManager linearLayoutManager;
    UserListAdapter adapter;
    ArrayList<QR> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_management);

        Log.v("QR 관리 페이지", "QR 관리 Activity 시작");


        Button createQrBtn = findViewById(R.id.button_createQR);
        createQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("QR 관리 페이지", "QR 발급 버튼 누름");

                Intent intent = new Intent(getApplicationContext(), CreateQRActivity.class);
                startActivity(intent);
            }
        });



        for (int i = 0; i < 5; i++) {
            items.add(new QR("qr" + i, "test" + i, getResources().getDrawable(R.drawable.sample_qr)));
        }

        userList = findViewById(R.id.ListView_QRList);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        adapter = new UserListAdapter(items);
        userList.setLayoutManager(linearLayoutManager);
        userList.setAdapter(adapter);
    }
}

class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.Holder> {

    ArrayList<QR> items = new ArrayList<>();

    public UserListAdapter(ArrayList<QR> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_qritem, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        QR item = items.get(position);
        holder.profileImg.setImageDrawable(item.getProfile());
        holder.qrName.setText(item.getQrName());
        holder.about.setText(item.getAbout());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ImageView profileImg;
        private TextView qrName;
        private TextView about;
        public Holder(@NonNull View itemView) {
            super(itemView);
            profileImg = itemView.findViewById(R.id.profile_img);
            qrName = itemView.findViewById(R.id.text_point_item);
            about = itemView.findViewById(R.id.text_aboutpoint);
        }
    }
}

