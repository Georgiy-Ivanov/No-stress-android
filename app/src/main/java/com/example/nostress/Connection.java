package com.example.nostress;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Connection{
    private  Socket  mSocket = null;
    private  String  mHost   = null;
    private  int     mPort   = 0;



    public static final String LOG_TAG = "SOCKET";

    public Connection (final String host, final int port)
    {
        this.mHost = host;
        this.mPort = port;
    }

    // Метод открытия сокета
    public void openConnection() throws Exception
    {
        // Если сокет уже открыт, то он закрывается
        closeConnection();
        try {
            // Создание сокета
            mSocket = new Socket(mHost, mPort);


        } catch (IOException e) {
            throw new Exception("Невозможно создать сокет: "
                    + e.getMessage());
        }
    }

    public void closeConnection()
    {
        if (mSocket != null && !mSocket.isClosed()) {
            try {
                mSocket.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Ошибка при закрытии сокета :"
                        + e.getMessage());
            } finally {
                mSocket = null;
            }
        }
        mSocket = null;
    }

    public void sendData(String data) throws Exception {
        // Проверка открытия сокета
        if (mSocket == null || mSocket.isClosed()) {
            throw new Exception("Ошибка отправки данных. " +
                    "Сокет не создан или закрыт");
        }
        // Отправка данных
        try {
            System.out.println("Начинаем отправлять данные");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream()));
            System.out.println("data: " + data);
            writer.write(data);
            writer.newLine();
            writer.flush();
            System.out.println("Отправили данные");
        } catch (IOException e) {
            throw new Exception("Ошибка отправки данных : "
                    + e.getMessage());
        }
    }

    public String getData() throws Exception {
        String reqest;
        if (mSocket == null || mSocket.isClosed()) {
            throw new Exception("Ошибка отправки данных. " +
                    "Сокет не создан или закрыт");
        }
        // Отправка данных
        try {
            System.out.println("Создаём буфферед ридер");
            BufferedReader reader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            System.out.println("Начинаем получать данные");
            reqest = reader.readLine();
            System.out.println("Request: " + reqest);
        } catch (IOException e) {
            throw new Exception("Ошибка отправки данных : "
                    + e.getMessage());
        }

        return reqest;
    }
    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        closeConnection();
    }
}