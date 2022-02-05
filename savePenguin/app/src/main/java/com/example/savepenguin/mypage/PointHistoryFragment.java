package com.example.savepenguin.mypage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savepenguin.IpSetting;
import com.example.savepenguin.account.LoginFragment;
import com.example.savepenguin.account.SharedPreference;
import com.example.savepenguin.mainpage.MainActivity;
import com.example.savepenguin.model.Point;
import com.example.savepenguin.R;
import com.example.savepenguin.model.QR;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class PointHistoryFragment extends Fragment {

    MyPageActivity myPageActivity;
    RecyclerView pointList;
    LinearLayoutManager linearLayoutManager;
    PointListAdapter adapter;
    public ArrayList<Point> items = new ArrayList<>();
    int userPoint;
    String userID;
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
        userID = SharedPreference.getAttribute(getActivity(), "userid");
        Button gobackBtn = viewGroup.findViewById(R.id.gobackBtn_pointHistory);

        gobackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPageActivity.onChangeFragment(0);
            }
        });
        pointList = viewGroup.findViewById(R.id.ListView_QRList);

        try {
            GetPointListTask task = new GetPointListTask();
            userPoint = Integer.parseInt(task.execute(userID).get());
            Log.v("메인 페이지", userID + "의 현재 포인트는 " + userPoint);
        } catch (Exception e) {

        }


        linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        adapter = new PointListAdapter(items);
        pointList.setLayoutManager(linearLayoutManager);
        pointList.setAdapter(adapter);

        TextView week, month, halfYear, year;
        week = viewGroup.findViewById(R.id.textView_week);
        month = viewGroup.findViewById(R.id.textView_month);
        halfYear = viewGroup.findViewById(R.id.textView_halfyear);
        year = viewGroup.findViewById(R.id.textView_year);

        // 1주 클릭
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("마이 페이지", "1주 클릭");

            }
        });

        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("마이 페이지", "1달 클릭");
            }
        });

        halfYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("마이 페이지", "반 년 클릭");
            }
        });

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("마이 페이지", "1년 클릭");
            }
        });

        return viewGroup;
    }

    class GetPointListTask extends AsyncTask<String, Void, String> {
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
                sendMsg = "userid=" + strings[0]; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                osw.flush();

                // jsp와 통신이 잘 되고, 서버에서 보낸 값 받음.
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                } else {    // 통신이 실패한 이유를 찍기위한 로그
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
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

            if (s != null) {
                JSONObject jsonObj = null;
                JSONArray qrList = null;
                try {
                    jsonObj = new JSONObject(s);
                    qrList = jsonObj.getJSONArray("Point");
                    System.out.println("길이 " + qrList.length());
                    for (int i = 0; i < qrList.length(); i++) {
                        JSONObject point = qrList.getJSONObject(i);
                        int cupPoint = point.getInt("cuppoint");
                        String qrName = point.getString("qrname");
                        String userName = point.getString("username");
                        String pointLocation = point.getString("pointLocation");
                        String pointDate = point.getString("pointDate");
                        items.add(new Point(cupPoint, pointDate, pointLocation, qrName, userName));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                System.out.println(s);
            }

        }
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
        holder.pointName.setText("사용 텀블러 : " + item.getQrname() + "        +" + item.getCuppoint() + "점");
        holder.aboutPoint.setText("획득 날짜"+item.getPointDate()+" 획득 위치 : "+item.getPointLocation());
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


