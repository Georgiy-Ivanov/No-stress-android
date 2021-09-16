package com.example.nostress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegistrationActivity extends AppCompatActivity {
    final Connection mConnect = (new Connection("5.228.40.252", 1234));
    private EditText email;
    private EditText password;
    private EditText passwordRepeat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        email = findViewById(R.id.email_field);
        password = findViewById(R.id.pass_field);
        passwordRepeat = findViewById(R.id.pass_field2);
        Button btn = findViewById(R.id.reg_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if ((password.getText().toString()).equals(passwordRepeat.getText().toString())) {
                        onOpenClick();
                    } else {
                        Toast toast = Toast.makeText(RegistrationActivity.this, "Пароли не совпадают", Toast.LENGTH_LONG);
                        toast.show();
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
                        if (getData(sndMsg).equals("1")) {
                            Intent intent = new Intent(RegistrationActivity.this, CreateProfileActivity.class);
                            intent.putExtra("email", userEmail);
                            startActivity(intent);
                        } else {
                            Toast toast = Toast.makeText(RegistrationActivity.this, "Такой email уже используется", Toast.LENGTH_LONG);
                            toast.show();
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

