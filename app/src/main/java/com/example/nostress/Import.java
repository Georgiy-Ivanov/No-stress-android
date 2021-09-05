package com.example.nostress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Import {
    Socket socket;
        public String getData(){
            String data = "2";
            try {
                socket = new Socket("5.228.40.252", 1234);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                data = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }
}
