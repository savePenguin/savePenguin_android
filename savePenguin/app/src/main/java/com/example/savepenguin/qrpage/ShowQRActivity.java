package com.example.savepenguin.qrpage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.savepenguin.IpSetting;
import com.example.savepenguin.R;
import com.example.savepenguin.account.SharedPreference;
import com.example.savepenguin.model.QR;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ShowQRActivity extends AppCompatActivity {
    private String userid;
    public ImageView qrImage;
    public TextView qrName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showqr);
        Log.v("QR 조회 페이지", "showQRActivity 시작");
        userid = SharedPreference.getAttribute(getApplicationContext(), "userid");
        Intent intent = getIntent(); //전달할 데이터를 받을 Intent
        // text 키값으로 데이터를 받는다. String을 받아야 하므로 getStringExtra()를 사용함
        String qrname = intent.getStringExtra("qrname");
        Log.v("QR 조회 페이지", "userid : " + userid + " qrname: " + qrname);

        qrImage = findViewById(R.id.imageView_QR);
        qrName = findViewById(R.id.textView_qrname_showqr);

        try {
            getQRTask task = new getQRTask();
            task.execute(userid, qrname);
            Log.v("QR 발급 페이지", "QR 발급 반영");
        } catch (Exception e) {

        }


    }
    class getQRTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        String id;
        IpSetting ipSetting = new IpSetting();
        @Override
        // doInBackground의 매개변수 값이 여러개일 경우를 위해 배열로
        protected String doInBackground(String... strings) {
            try {
                id = strings[0];
                String str;
                URL url = new URL(ipSetting.getBaseUrl() + "/specifyqrcode/" + strings[0] + "/" + strings[1]);  // 어떤 서버에 요청할지(localhost 안됨.)
                // ex) http://123.456.789.10:8080/hello/android
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                conn.setDoOutput(true);
                conn.setConnectTimeout(1000);

                // 서버에 보낼 값 포함해 요청함.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "userid=" + strings[0] + "&qrname=" + strings[1]; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
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

            JSONObject jsonObj = null;
            JSONArray qrList = null;
            try {
                jsonObj = new JSONObject(s);
                qrList = jsonObj.getJSONArray("qrlist");
                System.out.println("길이 " +qrList.length());
                for (int i = 0; i < qrList.length(); i++) {
                    JSONObject qr = qrList.getJSONObject(i);
                    String qrname = qr.getString("qrname");
                    String imageData = qr.getString("data");
                    System.out.println("qrname = " + qrname);
                    System.out.println("imageData = " + imageData);
                    //byte[] image = imageData.getBytes(StandardCharsets.UTF_8);
                    byte[] image = Base64.decode(imageData, Base64.DEFAULT);

                    //byte[] image = imageData.getBytes();
                    Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                    qrName.setText("QR 이름 : " + qrname);
                    qrImage.setImageBitmap(bmp);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(s);
        }
    }
}
