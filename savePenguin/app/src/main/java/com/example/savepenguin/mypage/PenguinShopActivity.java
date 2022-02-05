package com.example.savepenguin.mypage;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savepenguin.GetPointTask;
import com.example.savepenguin.IpSetting;
import com.example.savepenguin.R;
import com.example.savepenguin.account.SharedPreference;
import com.example.savepenguin.mainpage.MainActivity;
import com.example.savepenguin.mainpage.PenguinFragement;
import com.example.savepenguin.model.PenguinItem;
import com.example.savepenguin.model.Point;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class PenguinShopActivity extends AppCompatActivity {

    LinearLayoutManager linearLayoutManager;
    RecyclerView penguinItemList;
    PenguinItemListAdapter adapter;
    TextView text_user, text_point;
    ArrayList<PenguinItem> penguinItems;
    public static Context mContext;
    private String userid;
    public int userPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penguinshop);
        userid = SharedPreference.getAttribute(getApplicationContext(), "userid");
        mContext = this;
        text_user = findViewById(R.id.textView_userid_penguinshop);
        text_point = findViewById(R.id.textView_userpoint_penguinshop);

        //포인트 받아오는것
        try {
            GetPointTask getPointTask = new GetPointTask();
            userPoint = Integer.parseInt(getPointTask.execute(userid).get());
            Log.v("메인 페이지", userid + "의 현재 포인트는 " + userPoint);

        } catch (Exception e) {

        }

        text_user.setText(userid + "님");
        text_point.setText(userPoint + "점");

        penguinItemList = findViewById(R.id.ListView_penguinItem);

        GridLayoutManager manager = new GridLayoutManager(this, 3);
        penguinItemList.setLayoutManager(manager);
        penguinItems = new ArrayList<>();
        dummyData();
        adapter = new PenguinItemListAdapter(penguinItems);

        penguinItemList.setAdapter(adapter);

        System.out.println(adapter.imageCode);

    }

    public void dummyData() {

        penguinItems.add(new PenguinItem("item2", getResources().getDrawable(R.drawable.penguin2), 1000));
        penguinItems.add(new PenguinItem("item3", getResources().getDrawable(R.drawable.penguin3), 700));
        penguinItems.add(new PenguinItem("item4", getResources().getDrawable(R.drawable.penguin4), 500));
        penguinItems.add(new PenguinItem("item5", getResources().getDrawable(R.drawable.penguin5), 7000));
        penguinItems.add(new PenguinItem("item6", getResources().getDrawable(R.drawable.penguin6), 2500));

    }

    public void changePenguin(int imageCode) {
        Log.v("펭귄샵 페이지", "펭귄 이미지 변경");

        ((MainActivity) MainActivity.context).penguinView.setImageDrawable(penguinItems.get(imageCode).getItemImage());
        //포인트 갱신 필요

    }

    public int getUserPoint() {
        int curPoint = userPoint;
        try {
            getPointTask task = new getPointTask();
            String result = task.execute(userid).get();
            Log.v("펭귄샵 페이지", "통신 리턴값 : " + result);
        } catch (Exception e) {

        }
        userPoint = curPoint;
        updatePointText();

        return curPoint;
    }

    public void usePoint(int price) {
        userPoint = userPoint - price;
        Log.v("펭귄샵 페이지", "");
        try {
            updatePointTask task = new updatePointTask();
            String result = task.execute(userid, String.valueOf(userPoint)).get();
            Log.v("펭귄샵 페이지", "통신 리턴값 : " + result);
        } catch (Exception e) {

        }
        updatePointText();

    }

    public void updatePointText() {
        text_point.setText(userPoint + "점");
        ((MainActivity) MainActivity.context).text_userPoint.setText(userPoint + "점");
    }

    class getPointTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        String id;
        IpSetting ipSetting = new IpSetting();
        @Override
        // doInBackground의 매개변수 값이 여러개일 경우를 위해 배열로
        protected String doInBackground(String... strings) {
            try {
                id = strings[0];
                String str;
                URL url = new URL(ipSetting.getBaseUrl() + "/user/pointlist/" + strings[0]);  // 어떤 서버에 요청할지(localhost 안됨.)
                // ex) http://123.456.789.10:8080/hello/android
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                conn.setDoOutput(true);
                conn.setConnectTimeout(1000);

                // 서버에 보낼 값 포함해 요청함.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "userid="+strings[0]; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                osw.flush();

                // jsp와 통신이 잘 되고, 서버에서 보낸 값 받음.
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                } else {    // 통신이 실패한 이유를 찍기위한 로그
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 서버에서 보낸 값을 리턴합니다.
            return receiveMsg;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            System.out.println(s);
        }
    }
    class updatePointTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        String id;
        IpSetting ipSetting = new IpSetting();
        @Override
        // doInBackground의 매개변수 값이 여러개일 경우를 위해 배열로
        protected String doInBackground(String... strings) {
            try {
                id = strings[0];
                String str;
                URL url = new URL(ipSetting.getBaseUrl() + "/user/pointUpdate/" + strings[0]);  // 어떤 서버에 요청할지(localhost 안됨.)
                // ex) http://123.456.789.10:8080/hello/android
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                conn.setDoOutput(true);
                conn.setConnectTimeout(1000);

                // 서버에 보낼 값 포함해 요청함.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "userid="+strings[0]+"&userpoint="+strings[1]; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                osw.flush();

                // jsp와 통신이 잘 되고, 서버에서 보낸 값 받음.
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                } else {    // 통신이 실패한 이유를 찍기위한 로그
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 서버에서 보낸 값을 리턴합니다.
            return receiveMsg;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            System.out.println(s);
        }
    }
}



class PenguinItemListAdapter extends RecyclerView.Adapter<PenguinItemListAdapter.Holder> {

    ArrayList<PenguinItem> items = new ArrayList<>();
    public int imageCode = 0;


    public PenguinItemListAdapter(ArrayList<PenguinItem> items) {
        this.items = items;
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
        holder.itemImage.setImageDrawable(item.getItemImage());
        holder.itemName.setText(item.getItemName());
        holder.itemPrice.setText(item.getItemPrice() + "원");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView itemName, itemPrice;
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
                    String strP = itemPrice.getText().toString();
                    int price = Integer.parseInt(strP.substring(0, strP.length() - 1));
                    int userpoint = ((PenguinShopActivity) PenguinShopActivity.mContext).getUserPoint();
                    if (price <= userpoint) {
                        Log.v("펭귄 샵", "아이템 구매 성공");

                        ((PenguinShopActivity) PenguinShopActivity.mContext).usePoint(price);
                        ((PenguinShopActivity) PenguinShopActivity.mContext).changePenguin(getAdapterPosition());
                    } else {
                        Log.v("펭귄 샵", "아이템 구매 실패, 포인트 부족");

                    }


                }
            });
        }
    }
}
