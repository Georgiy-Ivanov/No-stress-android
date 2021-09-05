package com.example.nostress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateProfileActivity extends AppCompatActivity {
    final Connection mConnect = (new Connection("5.228.40.252", 1234));
    private EditText name;
    private EditText phone;
    private Button button;
    Calendar calendar;
    Intent intent;
    String email;
    SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_profile_activity);

        intent = getIntent();
        email = intent.getStringExtra("email");
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        name =findViewById(R.id.name_field);
        phone = findViewById(R.id.phone_field);
        button = findViewById(R.id.accept_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    buttonPressed();
            }
        });
    }

    private void buttonPressed(){
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        mConnect.openConnection();
                        String date_now = dateFormat.format(calendar.getTime());
                        String userName = name.getText().toString();
                        String userPhone = formatNumber(phone.getText().toString());
                        String sndMsg = "create;"+ email + ";" + date_now + ";" + userName + ";" + userPhone;
                        mConnect.sendData(sndMsg);
                        mConnect.closeConnection();

                        Intent intent = new Intent(CreateProfileActivity.this, MainActivity.class);
                        intent.putExtra("request", sndMsg);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
    }


    private static String formatNumber(String phone) { //форматирование номера телефона
        if ((phone.replaceAll("[^0-9]", "")).length() == 11) {
            String clean = phone.replaceAll("[^0-9]", "");
            String result = "+7" + " (" + clean.substring(1, 4) + ") " +
                    clean.substring(4, 7) + " " + clean.substring(7, 9) + " " + clean.substring(9);
            return result;
        }
        if ((phone.replaceAll("[^0-9]", "")).length() == 10) {
            String clean = "8".concat(phone.replaceAll("[^0-9]", ""));

            String result = "+7" + " (" + clean.substring(1, 4) + ") " +
                    clean.substring(4, 7) + " " + clean.substring(7, 9) + " " + clean.substring(9);
            return result;
        }
        return "+7 (000) 000 00 00";
    }

}