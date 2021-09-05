package com.example.nostress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegistrationActivity extends AppCompatActivity {
    final Connection mConnect = (new Connection("5.228.40.252", 1234));
    private EditText email;
    private EditText password;
    private EditText passwordRepeat;
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        email = findViewById(R.id.email_field);
        password = findViewById(R.id.pass_field);
        passwordRepeat = findViewById(R.id.pass_field2);
        btn = findViewById(R.id.reg_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if ((password.getText().toString()).equals(passwordRepeat.getText().toString())) {
                        onOpenClick();
                    } else {
                        System.out.println("Пароли не совпадают");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void onOpenClick(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                    try {
                        String userEmail = email.getText().toString();
                        String userPass = password.getText().toString();
                        String sndMsg = "reg;"+ userEmail + ";" + userPass;
                        sendData(sndMsg);
                        String isRegistered = mConnect.getData();
                        if (isRegistered.equals("1")) {
                            Intent intent = new Intent(RegistrationActivity.this, CreateProfileActivity.class);
                            intent.putExtra("email", userEmail);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }).start();
    }

    public void sendData(String msg){
        try {
            mConnect.openConnection();
            mConnect.sendData(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

