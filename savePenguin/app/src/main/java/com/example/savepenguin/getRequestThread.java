package com.example.savepenguin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class getRequestThread extends Thread {
    private String urlStr, result;

    getRequestThread(String urlStr) {
        this.urlStr = urlStr;
    }

    public void run(){

        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if(conn != null){
                conn.setConnectTimeout(10000); // 10초 대기 후 응답이 없으면 끝
                conn.setRequestMethod("GET");
                conn.setDoInput(true); // 서버에서 받기 가능
                conn.setDoOutput(true); // 서버에 보내기 가능

                int resCode = conn.getResponseCode(); // 서버에 연결 시도

                // reader는 바이트 배열이 아니라, 문자열로 처리할 수 있음
                // BufferedReader는 한줄씩 읽어들임
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;

                while(true){
                    line = reader.readLine();
                    if(line == null)
                        break;
                    System.out.println(line);
                    result += line + "\n";
                }

                reader.close();
                conn.disconnect();
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getResult() {
        return result;
    }

}
