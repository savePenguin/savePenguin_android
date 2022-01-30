package com.example.savepenguin;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CreateQRActivity extends AppCompatActivity {

    private Button makeQRBtn, deletePicBtn, submitPicBtn, goBackBtn;
    private ImageView cupPreview;
    private RadioButton cupTypeBtn1, cupTypeBtn2;
    private RadioGroup cupTypesGroup;
    private TextView text_cupPicName;
    private EditText text_cupName;
    private String cupImageFileName, cupName = "temp";
    private int cupType = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createqr);
        Log.v("QR 발급 페이지", "QR 발급 Activity 시작");

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

//        try {
//            String imgpath = getCacheDir() + "/" + cupImageFileName;
//            Bitmap bm = BitmapFactory.decodeFile(imgpath);
//            cupPreview.setImageBitmap(bm);   // 내부 저장소에 저장된 이미지를 이미지뷰에 셋
//            Toast.makeText(getApplicationContext(), "파일 로드 성공", Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), "파일 로드 실패", Toast.LENGTH_SHORT).show();
//        }

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
                        if (flist[i].getName().equals(cupName+".png")) {   // 삭제하고자 하는 이름과 같은 파일명이 있으면 실행
                            flist[i].delete();  // 파일 삭제
                            Toast.makeText(getApplicationContext(), "파일 삭제 성공", Toast.LENGTH_SHORT).show();
                            initPicRegister(); //사진등록 폼 숨기기
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "파일 삭제 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("QR 발급 페이지", "뒤로 가기 버튼 누름");
                finish();
            }
        });

        makeQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qrname = text_cupName.getText().toString();
                Bitmap qrpic = cupPreview.getDrawingCache();
                int cuptype = cupType;

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
                Log.v("QR 발급 페이지", "fileUri : " + fileUri);
                ContentResolver resolver = getContentResolver();
                try {
                    InputStream instream = resolver.openInputStream(fileUri);
                    Bitmap imgBitmap = BitmapFactory.decodeStream(instream);
                    cupPreview.setImageBitmap(imgBitmap);    // 선택한 이미지 이미지뷰에 셋
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
        File tempFile = new File(getCacheDir(), cupName+".png");    // 파일 경로와 이름 넣기
        try {
            tempFile.createNewFile();   // 자동으로 빈 파일을 생성하기
            FileOutputStream out = new FileOutputStream(tempFile);  // 파일을 쓸 수 있는 스트림을 준비하기
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);   // compress 함수를 사용해 스트림에 비트맵을 저장하기
            out.close();    // 스트림 닫아주기
            Toast.makeText(getApplicationContext(), "파일 저장 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "파일 저장 실패", Toast.LENGTH_SHORT).show();
        }
    }

    // 선택된 이미지 파일명 가져오기
    public String getImageNameToUri(Uri data)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

        return imgName;
    }

}
