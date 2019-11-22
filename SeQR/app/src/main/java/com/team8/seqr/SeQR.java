package com.team8.seqr;

public class SeQR {

    public static SeQR getInstance(String type) {
        if (type == "Client") {
            return SeQRClient.getInstance();
        }
        if (type == "Server") {
            return SeQRServer.getInstance();
        }
        return SeQRClient.getInstance();
    }

    void startConnection() {

    }
}
