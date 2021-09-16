package com.example.nostress;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    final Connection mConnect = (new Connection("5.228.40.252", 1234));
    String email;
    TextView user_email;
    TextView date;
    TextView name;
    TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        user_email = findViewById(R.id.email_user_field);
        date = findViewById(R.id.date_filed);
        name = findViewById(R.id.name_user_field);
        phone = findViewById(R.id.phone_user_field);
        getUserInfo();


    }

    public void getUserInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String mass[];
                    String request = getData("get;" + email);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setTExtfields(request);
                        }
                    });

                } catch (Exception e) {
                    System.out.println("чот не так1");
                }
            }
        }).start();
    }

    public void setTExtfields(String string){
        String mass[];
        try{
        mass = string.split(";");
        if (mass[0].equals("get")) {
            user_email.setText(email);
            date.setText(mass[1]);
            name.setText(mass[2]);
            phone.setText(mass[3]);
        }
        if (mass[0].equals("2")) {
            Intent intent2 = new Intent(MainActivity.this, CreateProfileActivity.class);
            intent2.putExtra("email", email);
            startActivity(intent2);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
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
