package com.example.savepenguin.qrpage;

import android.content.ContentValues;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class FileUploadUtils {
    public static void send2Server(String url, String imagePath, ContentValues info) {
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
        try {
            File sourceFile = new File(imagePath);
            Log.d("TAG", "File...::::" + sourceFile + " : " + sourceFile.exists());
            String filename = imagePath.substring(imagePath.lastIndexOf("/") + 1);


            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("userid", (String) info.get("userid"))
                    .addFormDataPart("qrname", (String) info.get("qrname"))
                    .addFormDataPart("cuptype", String.valueOf(info.get("cuptype")))
                    .addFormDataPart("cuppic", filename, RequestBody.create(MEDIA_TYPE_PNG, sourceFile))
                    //.addFormDataPart("files", file.getName(), RequestBody.create(MultipartBody.FORM, file))
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


