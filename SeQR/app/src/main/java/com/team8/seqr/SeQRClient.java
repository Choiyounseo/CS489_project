package com.team8.seqr;

public class SeQRClient extends SeQR {
    private static SeQRClient instance;
    private Encryptor encryptor;

    private SeQRClient() {

    }

    public static SeQR getInstance() {
        if (instance == null) {
            instance = new SeQRClient();
        }
        return instance;
    }

    @Override
    void startConnection() {
        super.startConnection();


    }
}
