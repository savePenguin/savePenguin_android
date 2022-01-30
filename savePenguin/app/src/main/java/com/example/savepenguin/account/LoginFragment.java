package com.example.savepenguin.account;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.savepenguin.HttpClient;
import com.example.savepenguin.MainActivity;
import com.example.savepenguin.R;
import com.example.savepenguin.RequestHttpURLConnection;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LoginFragment extends Fragment {

    LoginActivity loginActivity;
    private boolean isAccountValid = false;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        loginActivity = (LoginActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loginActivity = null;
    }



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        Log.v("로그인 페이지", "로그인 Fragment 시작");
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);

        TextView text_id = viewGroup.findViewById(R.id.textbox_userId);
        TextView text_pw = viewGroup.findViewById(R.id.textbox_userPw);


        Button loginBtn = viewGroup.findViewById(R.id.button_login);
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 로그인 버튼 눌러서 메인 페이지로 진입
                // 로그인 버튼을 눌렀을 때 그 정보가 유효함을 검사 해야함
                Log.v("로그인 페이지", "로그인 버튼 누름");
                String id, pw;
                id = text_id.getText().toString();
                pw = text_pw.getText().toString();
                Log.v("로그인 페이지", "id : " + id + " pw : " + pw);

                // URL 설정.
                String url = "http://192.168.219.110:8060/auth/signin";

                ContentValues loginInfo = new ContentValues();
                loginInfo.put("userid", id);
                loginInfo.put("userpw", pw);


                //입력 누락되었는지 확인 후 계정이 유효한지 확인
                if (isValidInput(id) && isValidInput(pw)) {
                     //AsyncTask를 통해 HttpURLConnection 수행.
//                    NetworkTask networkTask = new NetworkTask(url, loginInfo);
//                    networkTask.execute();
                    try {
                        CustomTask task = new CustomTask();
                        String result = task.execute(id,pw).get();
                        Log.v("로그인 페이지", "통신 리턴값 : " + result);
                    } catch (Exception e) {

                    }
//                    LoginCheck loginCheck = new LoginCheck(id, pw);
//                    loginCheck.tryLogin();

//                    NetworkTask2 networkTask = new NetworkTask2();
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("userid", id);
//                    params.put("userpw", pw);
//
//                    networkTask.execute(params);





                } else {
                    Toast.makeText(getContext(), "id, pw 입력 중 누락된 것이 존재", Toast.LENGTH_SHORT).show();
                    Log.v("로그인 페이지", "id, pw 입력 중 누락된 것이 존재");
                }

            }
        });

        Button registerBtn = viewGroup.findViewById(R.id.button_register);
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.v("로그인 페이지", "회원가입 버튼 누름");
                loginActivity.onChangeFragment(1);
            }
        });

        TextView findId = viewGroup.findViewById(R.id.textView_findId);
        findId.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.v("로그인 페이지", "아이디찾기 버튼 누름");
                loginActivity.onChangeFragment(2);
            }
        });

        TextView findPw = viewGroup.findViewById(R.id.textView_findPw);
        findPw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.v("로그인 페이지", "비밀번호찾기 버튼 누름");
                loginActivity.onChangeFragment(3);
            }
        });

        return viewGroup;

    }

    public static boolean isValidInput(String input) {
        if (input.equals("") | input == null) {
            return false;
        } else {
            return true;
        }
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            //리턴 결과로 로그인 성공 실패 여부 확인
            isAccountValid = true;

            if (isAccountValid) {
                Log.v("로그인 페이지", "로그인 버튼 성공");

                Log.v("로그인 페이지", "로그인 정보 가져오기");
                SharedPreference.setAttribute(getContext(), "userid", "temp");

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            } else {
                Log.v("로그인 페이지", "로그인 실패");
                Toast.makeText(getActivity(),"아이디나 비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show();
            }
            System.out.println(s);
        }
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        // doInBackground의 매개변수 값이 여러개일 경우를 위해 배열로
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://192.168.219.110:8060/TestLogin");  // 어떤 서버에 요청할지(localhost 안됨.)
                // ex) http://123.456.789.10:8080/hello/android
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                conn.setDoOutput(true);

                // 서버에 보낼 값 포함해 요청함.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "userid="+strings[0]+"&userpw="+strings[1]; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
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

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            //리턴 결과로 로그인 성공 실패 여부 확인
            isAccountValid = true;

            if (isAccountValid) {
                Log.v("로그인 페이지", "로그인 버튼 성공");

                Log.v("로그인 페이지", "로그인 정보 가져오기");
                SharedPreference.setAttribute(getContext(), "userid", "temp");

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            } else {
                Log.v("로그인 페이지", "로그인 실패");
                Toast.makeText(getActivity(),"아이디나 비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show();
            }
            System.out.println(s);
        }
    }

    public class NetworkTask2 extends AsyncTask<Map<String, String>, Integer, String> {
        @Override
        protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

            // Http 요청 준비 작업
            HttpClient.Builder http = new HttpClient.Builder("POST", "http://192.168.219.110:8060/TestLogin");

            // Parameter 를 전송한다.
            http.addAllParameters(maps[0]);

            //Http 요청 전송
            HttpClient post = http.create();
            post.request();

            // 응답 상태코드 가져오기
            int statusCode = post.getHttpStatusCode();
            // 응답 본문 가져오기
            String body = post.getBody();

            return body;
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }


}