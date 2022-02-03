package com.example.savepenguin.qrpage;

import android.content.ContentValues;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class FileUploadUtils {
    public static void send2Server(String url, File file, ContentValues info) {

        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("userid", (String) info.get("userid"))
                .addFormDataPart("qrname", (String) info.get("qrname"))
                .addFormDataPart("cuptype", String.valueOf(info.get("cuptype")))
                .setType(MultipartBody.FORM)
                .addFormDataPart("files", file.getName(), RequestBody.create(MultipartBody.FORM, file))
                .build();

        Request request = new Request.Builder()
                .url(url) // Server URL 은 본인 IP를 입력
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("TEST : ", response.body().string());
                    }
                });
    }
}


