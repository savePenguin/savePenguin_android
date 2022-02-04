package com.example.savepenguin.mypage;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savepenguin.R;
import com.example.savepenguin.account.SharedPreference;
import com.example.savepenguin.mainpage.MainActivity;
import com.example.savepenguin.mainpage.PenguinFragement;
import com.example.savepenguin.model.PenguinItem;
import com.example.savepenguin.model.Point;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class PenguinShopActivity extends AppCompatActivity {

    LinearLayoutManager linearLayoutManager;
    RecyclerView penguinItemList;
    PenguinItemListAdapter adapter;
    TextView text_user, text_point;
    ArrayList<PenguinItem> penguinItems;

    private String userid;
    private int userPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penguinshop);
        userid = SharedPreference.getAttribute(getApplicationContext(), "userid");

        text_user = findViewById(R.id.textView_userid_penguinshop);
        text_point = findViewById(R.id.textView_userpoint_penguinshop);

        //포인트 받아오는것
        userPoint = 1000;

        text_user.setText(userid + "님");
        text_point.setText(userPoint + "점");

        penguinItemList = findViewById(R.id.ListView_penguinItem);

        GridLayoutManager manager = new GridLayoutManager(this, 3);
        penguinItemList.setLayoutManager(manager);
        penguinItems = new ArrayList<>();
        dummyData();
        adapter = new PenguinItemListAdapter(penguinItems, getApplicationContext());

        penguinItemList.setAdapter(adapter);
        
    }

    public void dummyData() {

        penguinItems.add(new PenguinItem("item2", getResources().getDrawable(R.drawable.penguin2), 1000));
        penguinItems.add(new PenguinItem("item3", getResources().getDrawable(R.drawable.penguin3), 700));
        penguinItems.add(new PenguinItem("item4", getResources().getDrawable(R.drawable.penguin4), 500));
        penguinItems.add(new PenguinItem("item5", getResources().getDrawable(R.drawable.penguin5), 7000));
        penguinItems.add(new PenguinItem("item6", getResources().getDrawable(R.drawable.penguin6), 2500));

    }

}

class PenguinItemListAdapter extends RecyclerView.Adapter<PenguinItemListAdapter.Holder> {

    ArrayList<PenguinItem> items = new ArrayList<>();
    Context context;

    public PenguinItemListAdapter(ArrayList<PenguinItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public PenguinItemListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_penguinitem, parent, false);
        return new PenguinItemListAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PenguinItemListAdapter.Holder holder, int position) {
        PenguinItem item = items.get(position);
        System.out.println(position+" "+item.getItemName());
        holder.itemImage.setImageDrawable(item.getItemImage());
        holder.itemName.setText(item.getItemName());
        holder.itemPrice.setText(item.getItemPrice() + "원");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView itemName,itemPrice;
        private ImageView itemImage;
        private Button buyBtn;
        public Holder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.textView_itemName);
            itemImage = itemView.findViewById(R.id.penguin_Item_img);
            itemPrice = itemView.findViewById(R.id.textView_itemPrice);
            buyBtn = itemView.findViewById(R.id.button_buyItem);

            buyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("펭귄 샵", itemName.getText().toString() + " 아이템 구매");

                    // 유저 아이디로 포인트조회하여 구매 가능한지 => 구매 되면 포인트 차감, 화면의 잔여 포인트 갱신
                    // + 메인 화면의 펭귄 사진 변경

                    MainActivity fragment = new MainActivity();
                    Bundle bundle = new Bundle();

                    bundle.putInt("penguinImage", R.id.penguin_Item_img);
                    Fragment penguinFragment = new Fragment();
                    penguinFragment.setArguments(bundle);
                    //tran.replace(R.id.fragment_main, penguinFragment).commit();

                }
            });
        }
    }
}
