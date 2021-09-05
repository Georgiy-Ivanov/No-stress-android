package com.example.nostress;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView email;
    private TextView date;
    private TextView name;
    private TextView phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Intent intent = getIntent();
        String request = intent.getStringExtra("request");
        email = findViewById(R.id.email_user_field);
        date = findViewById(R.id.date_filed);
        name = findViewById(R.id.name_user_field);
        phone = findViewById(R.id.phone_user_field);
        try {
            String mass[];
            mass = request.split(";");
            email.setText(mass[1]);
            date.setText(mass[2]);
            name.setText(mass[3]);
            phone.setText(mass[4]);
        } catch (Exception e) { }

    }
}
