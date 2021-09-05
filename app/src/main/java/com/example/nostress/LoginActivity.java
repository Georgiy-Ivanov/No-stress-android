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
    private Button loginBtn;
    private Button registrationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        email = findViewById(R.id.email_field);
        //String ololo = String.valueOf(email.getText());
        //Toast.makeText(MainActivity.this, ololo, Toast.LENGTH_LONG).show();
        password = findViewById(R.id.pass_field);
        loginBtn = findViewById(R.id.login_button);
        registrationBtn = findViewById(R.id.testpage);
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
                final String[] request = new String[1];

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            String userEmail = email.getText().toString();
                            String userPass = password.getText().toString();
                            String sndMsg = "log;" + userEmail + ";" + userPass;
                            request[0] = getData(sndMsg);

                            if (request[0].equals("1")) {
                                sndMsg = "get;" + userEmail;
                                request[0] = getData(sndMsg);
                                try {
                                    String[] answer;
                                    System.out.println("начинаем разделять");
                                    answer = request[0].split(";");
                                    System.out.println(answer[0]);
                                    if (answer[0].equals("get")){
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("email", userEmail);
                                        intent.putExtra("request", request[0]);
                                        startActivity(intent);
                                    }
                                    if (answer[0].equals("nope")){
                                        Intent intent = new Intent(LoginActivity.this, CreateProfileActivity.class);
                                        intent.putExtra("email", userEmail);
                                        startActivity(intent);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                            if (request[0].equals("2")) {
                                System.out.println("неа");
                            }
                            mConnect.closeConnection();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
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



