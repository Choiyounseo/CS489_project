package com.team8.seqr;

public class SeQRServer extends SeQR {
    private static SeQRServer instance;
    private Encryptor encryptor;

    private SeQRServer() {

    }

    public static SeQR getInstance() {
        if (instance == null) {
            instance = new SeQRServer();
        }
        return instance;
    }

    @Override
    void startConnection() {
        super.startConnection();


    }
}
