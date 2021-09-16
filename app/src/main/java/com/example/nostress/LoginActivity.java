package com.example.nostress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    final Connection mConnect = (new Connection("5.228.40.252", 1234));
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        email = findViewById(R.id.email_field);
        password = findViewById(R.id.pass_field);
        Button loginBtn = findViewById(R.id.login_button);
        Button registrationBtn = findViewById(R.id.testpage);
        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPressed();
            }
        });
    }

    public void buttonPressed(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String userEmail = email.getText().toString();
                    String userPass = password.getText().toString();
                    String sndMsg = "log;" + userEmail + ";" + userPass;
                    if (getData(sndMsg).equals("1")) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("email", userEmail);
                        startActivity(intent);
                    } else {
                        System.out.println("а должно бы");
                        Toast.makeText(LoginActivity.this, "Имя пользователя или пароль введены неверно", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public String getData(String msg){
        String message = "";
        try {
            mConnect.openConnection();
            mConnect.sendData(msg);
            message = mConnect.getData();
            mConnect.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
}



