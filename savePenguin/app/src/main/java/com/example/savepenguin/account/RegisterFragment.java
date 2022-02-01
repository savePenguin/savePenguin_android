package com.example.savepenguin.account;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.savepenguin.R;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;


public class RegisterFragment extends Fragment {

    LoginActivity loginActivity;

    private boolean isEmailValid, isTimeout;
    private String validCode;
    private TextView text_id,text_pw,text_name, text_email, text_validNumber, text_valid;
    private Button checkCodeBtn, goBackBtn, checkEmailBtn, registerBtn;

    MainHandler mainHandler;
    static int timer;
    int mailSend = 0;

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
        Log.v("로그인 페이지", "회원가입 Fragment 시작");
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_register, container, false);

        text_id = viewGroup.findViewById(R.id.editText_id);
        text_pw = viewGroup.findViewById(R.id.editText_pw);
        text_name = viewGroup.findViewById(R.id.editText_name);
        text_email = viewGroup.findViewById(R.id.editText_email);
        text_validNumber = viewGroup.findViewById(R.id.editText_validNumber);

        //인증 텍스트 투명
        text_valid = viewGroup.findViewById(R.id.text_validnumber);
        text_valid.setVisibility(View.GONE);
        //인증코드 입력창 투명
        text_validNumber.setVisibility(View.GONE);

        checkCodeBtn = viewGroup.findViewById(R.id.button_codecheck);
        checkCodeBtn.setVisibility(View.GONE);

        isEmailValid = false;

        //뒤로가기 버튼
        goBackBtn = viewGroup.findViewById(R.id.button_goback);
        goBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.v("회원가입 페이지", "뒤로가기 버튼 누름");
                loginActivity.onChangeFragment(0);
            }
        });

        //이메일 인증 버튼
        checkEmailBtn = viewGroup.findViewById(R.id.button_checkemail);
        checkEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = text_email.getText().toString();
                Log.v("회원가입 페이지", "이메일 인증 버튼 누름");

                MailThread(email).start();
                isEmailValid = false;
                timer = 0;

                if (timer == 0) {
                    timer = 180;
                    BackgroundThread backgroundThread = new BackgroundThread();
                    backgroundThread.start();
                    mailSend += 1;

                } else {
                    timer = 180;
                }
                //감춰져 있던 인증 파트 보여지게 설정
                text_valid.setVisibility(View.VISIBLE);
                text_validNumber.setVisibility(View.VISIBLE);
                checkCodeBtn.setVisibility(View.VISIBLE);

                mainHandler = new MainHandler();

            }
        });


        checkCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //인증 코드 일치
                if (text_validNumber.getText().toString().equals(validCode)) {
                    Log.v("회원가입 페이지", "이메일 인증 코드가 일치합니다");
                    isEmailValid = true;
                } else {
                    Log.v("회원가입 페이지", "이메일 인증 코드가 불일치\n ans :" + validCode + " input : " + text_validNumber.getText().toString());
                    Toast.makeText(getContext(), "인증코드 불일치", Toast.LENGTH_SHORT).show();
                }
                text_valid.setVisibility(View.GONE);
                text_validNumber.setVisibility(View.GONE);
                checkCodeBtn.setVisibility(View.GONE);
                text_validNumber.setText("");

            }
        });



        //회원 가입 버튼
        registerBtn = viewGroup.findViewById(R.id.button_submit);
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.v("회원가입 페이지", "확인(회원가입) 버튼 누름");
                String id, pw, name, email;
                id = text_id.getText().toString();
                pw = text_pw.getText().toString();
                name = text_name.getText().toString();
                email = text_email.getText().toString();
                Log.v("회원가입 페이지", "id : " + id + " pw : " + pw + " name : " + name + " email : " + email);

                if (isValidInput(id) && isValidInput(pw) && isValidInput(name) && isValidInput(email)) {

                    // 입력 유효성만 검사한 후 서버에 회원 정보 보냄
                    // 서버에서 중복성 검사 후 결과 리턴
                    // 이메일 인증 확인 
                    if (isEmailValid) {
                        Log.v("회원가입 페이지", "회원가입 성공");
                        text_id.setText("");
                        text_name.setText("");
                        text_pw.setText("");
                        text_email.setText("");
                        loginActivity.onChangeFragment(0);
                    } else {
                        Log.v("회원가입 페이지", "이메일 인증 실패");
                        Toast.makeText(getContext(), "이메일 인증이 누락되었습니다", Toast.LENGTH_SHORT).show();
                    }
                    
                } else {
                    Log.v("회원가입 페이지", "입력 누락 ");
                    Toast.makeText(getActivity(), "회원 정보 입력이 누락되었습니다", Toast.LENGTH_SHORT).show();
                }

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

    Thread MailThread(String targetEmail) {
        return new Thread() {
            @Override
            public void run() {
                super.run();

                GmailSender gMailSender = new GmailSender("fortestandwork97@gmail.com", "bs970923!!");
                //GMailSender.sendMail(제목, 본문내용, 받는사람);
                try {
                    gMailSender.sendMail("savePenguin 이메일 인증코드 ", "인증코드 : " + gMailSender.getEmailCode(), targetEmail);
                    validCode = gMailSender.getEmailCode();
                } catch (SendFailedException e) {

                    //쓰레드에서는 Toast를 띄우지 못하여 runOnUiThread를 호출해야 한다.
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (MessagingException e) {
                    System.out.println("인터넷 문제" + e);

                    //쓰레드에서는 Toast를 띄우지 못하여 runOnUiThread를 호출해야 한다.
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "인터넷 연결을 확인 해 주십시오", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //쓰레드에서는 Toast를 띄우지 못하여 runOnUiThread를 호출해야 한다.
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "송신 완료", Toast.LENGTH_SHORT).show();
                        Log.v("회원가입 페이지", "인증번호 전송 완료");
                    }
                });

            }
        };
    }

    //시간초가 카운트 되는 쓰레드
    class BackgroundThread extends Thread {

        //180초는 3분
        //메인 쓰레드에 value를 전달하여 시간초가 카운트다운 되게 한다.

        public void run() {
            //180초 보다 밸류값이 작거나 같으면 계속 실행시켜라
            while (true) {
                timer -= 1;
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }

                Message message = mainHandler.obtainMessage();
                //메세지는 번들의 객체 담아서 메인 핸들러에 전달한다.
                Bundle bundle = new Bundle();
                bundle.putInt("timer", timer);
                message.setData(bundle);

                //핸들러에 메세지 객체 보내기기

                mainHandler.sendMessage(message);

                if (timer <= 0) {
                    validCode = createEmailCode();
                    break;
                }
            }
        }
    }

    //쓰레드로부터 메시지를 받아 처리하는 핸들러
    //메인에서 생성된 핸들러만이 Ui를 컨트롤 할 수 있다.
    class MainHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int min, sec;

            Bundle bundle = message.getData();
            int timer = bundle.getInt("timer");

            min = timer / 60;
            sec = timer % 60;
            //초가 10보다 작으면 앞에 0이 더 붙어서 나오도록한다.
            if (sec < 10) {
                //텍스트뷰에 시간초가 카운팅
                text_validNumber.setHint("0" + min + " : 0" + sec);
            } else {
                text_validNumber.setHint("0" + min + " : " + sec);
            }
        }
    }
    private String createEmailCode() { //이메일 인증코드 생성
        String[] str = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String newCode = new String();

        for (int x = 0; x < 6; x++) {
            int random = (int) (Math.random() * str.length);
            newCode += str[random];
        }

        return newCode;
    }
}