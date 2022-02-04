package com.example.savepenguin.qrpage;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.savepenguin.IpSetting;
import com.example.savepenguin.R;
import com.example.savepenguin.account.SharedPreference;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



public class CreateQRActivity extends AppCompatActivity {

    private Button makeQRBtn, deletePicBtn, submitPicBtn, goBackBtn;
    private ImageView cupPreview;
    private RadioButton cupTypeBtn1, cupTypeBtn2;
    private RadioGroup cupTypesGroup;
    private TextView text_cupPicName;
    private EditText text_cupName;
    private String cupImageFileName, cupName = "temp";
    private int cupType = 0;
    private File tempImage, tempFile;
    private boolean haveImage;
    IpSetting ipSetting = new IpSetting();
    private String userid, fileURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createqr);
        Log.v("QR 발급 페이지", "QR 발급 Activity 시작");
        userid = SharedPreference.getAttribute(getApplicationContext(), "userid");

        makeQRBtn = findViewById(R.id.makeQRBtn);
        deletePicBtn = findViewById(R.id.button_deletepic);
        submitPicBtn = findViewById(R.id.button_submitpic);
        goBackBtn = findViewById(R.id.goBackBtn);
        cupTypeBtn1 = findViewById(R.id.radio_cuptype1);
        cupTypeBtn2 = findViewById(R.id.radio_cuptype2);
        cupTypesGroup = findViewById(R.id.radioGroup_cupTypes);
        cupPreview = findViewById(R.id.imageView_cupPreview);

        text_cupName = findViewById(R.id.editText_cupName);
        text_cupPicName = findViewById(R.id.textView_cupPicName);
        cupTypesGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);
        haveImage = false;
        cupPreview.setVisibility(View.GONE);
        deletePicBtn.setVisibility(View.GONE);
        text_cupPicName.setVisibility(View.GONE);

        // 사진 등록 버튼
        submitPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("QR 발급 페이지", "사진 등록 버튼 누름");

                cupName = text_cupName.getText().toString();

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 101);

            }
        });

        deletePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("QR 발급 페이지", "사진 삭제 버튼 누름");
                try {
                    File file = getCacheDir();  // 내부저장소 캐시 경로를 받아오기
                    System.out.println(getCacheDir());
                    File[] flist = file.listFiles();
                    for (int i = 0; i < flist.length; i++) {    // 배열의 크기만큼 반복
                        System.out.println(flist[i].getName());
                        if (flist[i].getName().equals(cupName + ".png")) {   // 삭제하고자 하는 이름과 같은 파일명이 있으면 실행
                            flist[i].delete();  // 파일 삭제
                            haveImage = false;
                            Toast.makeText(getApplicationContext(), "파일 삭제 성공", Toast.LENGTH_SHORT).show();
                            initPicRegister(); //사진등록 폼 숨기기
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "파일 삭제 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //뒤로가기 버튼
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("QR 발급 페이지", "뒤로 가기 버튼 누름");
                finish();
            }
        });

        //QR 발급 버튼
        makeQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qrname = text_cupName.getText().toString();
                int cuptype = cupType;

                Log.v("QR 발급 페이지", "QR 발급 버튼 누름");
                if (qrname.equals("") || !haveImage) {
                    Log.v("QR 발급 페이지", "입력 누락");
                    Toast.makeText(getApplicationContext(), "입력 누락", Toast.LENGTH_SHORT).show();
                } else {
                    Log.v("QR 발급 페이지", "정상 입력");
                    ContentValues qrInfo = new ContentValues();
                    qrInfo.put("userid", userid);
                    qrInfo.put("cuptype", cuptype);
                    qrInfo.put("qrname", qrname);

                    FileUploadUtils.send2Server(ipSetting.getBaseUrl() + "/qrcodetest", fileURL, qrInfo);
                    Toast.makeText(getApplicationContext(), "QR 발급", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {// 0.5 초 후에 실행
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 500);
                }
            }
        });
    }


    public void initPicRegister() {
        text_cupPicName.setText("");
        cupPreview.setImageBitmap(null);

        cupPreview.setVisibility(View.GONE);
        deletePicBtn.setVisibility(View.GONE);
        text_cupPicName.setVisibility(View.GONE);
    }

    public void finishPicRegister() {
        text_cupPicName.setText(cupName);

        cupPreview.setVisibility(View.VISIBLE);
        deletePicBtn.setVisibility(View.VISIBLE);
        text_cupPicName.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // 갤러리
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Uri fileUri = data.getData();
                fileURL = createCopyAndReturnRealPath(getApplicationContext(), fileUri);
                System.out.println("fileURL = " + fileURL);;

                Log.v("QR 발급 페이지", "fileUri : " + fileUri);
                ContentResolver resolver = getContentResolver();
                try {
                    InputStream instream = resolver.openInputStream(fileUri);
                    Bitmap imgBitmap = BitmapFactory.decodeStream(instream);

                    cupPreview.setImageBitmap(imgBitmap);    // 선택한 이미지 이미지뷰에 셋
                    haveImage = true;
                    finishPicRegister();
                    instream.close();   // 스트림 닫아주기

                    saveBitmapToJpeg(imgBitmap);    // 내부 저장소에 저장
                    Toast.makeText(getApplicationContext(), "파일 불러오기 성공", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "파일 불러오기 실패", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if (i == R.id.radio_cuptype1) {
                Toast.makeText(getApplicationContext(), "라디오 그룹 버튼1 눌렸습니다.", Toast.LENGTH_SHORT).show();
                Log.v("QR 발급 페이지", "컵 종류 1번째 버튼 선택");
                cupType = 0;
            } else if (i == R.id.radio_cuptype2) {
                Toast.makeText(getApplicationContext(), "라디오 그룹 버튼2 눌렸습니다.", Toast.LENGTH_SHORT).show();
                Log.v("QR 발급 페이지", "컵 종류 2번째 버튼 선택");
                cupType = 1;
            }
        }
    };

    public void saveBitmapToJpeg(Bitmap bitmap) {   // 선택한 이미지 내부 저장소에 저장
        tempFile = new File(getCacheDir(), cupName + ".png");    // 파일 경로와 이름 넣기
        try {
            tempFile.createNewFile();   // 자동으로 빈 파일을 생성하기
            tempImage.createNewFile();
            FileOutputStream out = new FileOutputStream(tempFile);  // 파일을 쓸 수 있는 스트림을 준비하기
            FileOutputStream out2 = new FileOutputStream(tempImage);  // 파일을 쓸 수 있는 스트림을 준비하기

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);   // compress 함수를 사용해 스트림에 비트맵을 저장하기
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out2);   // compress 함수를 사용해 스트림에 비트맵을 저장하기

            out.close();    // 스트림 닫아주기
            out2.close();

            Toast.makeText(getApplicationContext(), "파일 저장 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "파일 저장 실패", Toast.LENGTH_SHORT).show();
        }
    }

    // 절대경로 파악할 때 사용된 메소드
    @Nullable
    public static String createCopyAndReturnRealPath(@NonNull Context context, @NonNull Uri uri) {

        final ContentResolver contentResolver = context.getContentResolver();

        if (contentResolver == null)
            return null;

        // 파일 경로를 만듬
        String filePath = context.getApplicationInfo().dataDir + File.separator
                + System.currentTimeMillis();


        File file = new File(filePath);
        try {
            // 매개변수로 받은 uri 를 통해  이미지에 필요한 데이터를 불러 들인다.

            InputStream inputStream = contentResolver.openInputStream(uri);
            if (inputStream == null)
                return null;
            // 이미지 데이터를 다시 내보내면서 file 객체에  만들었던 경로를 이용한다.


            OutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                outputStream.write(buf, 0, len);
            outputStream.close();

            inputStream.close();

        } catch (IOException ignore) {
            return null;
        }

        return file.getAbsolutePath();
    }

}
