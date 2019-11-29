package com.team8.seqr;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class SeQRServer extends SeQR {

    private Encryptor encryptor;

    private String publicKey;
    private String privateKey;
    private String secretKey;



    public SeQRServer() {
        encryptor = new Encryptor();
        publicKey = encryptor.getPublicKey();
        privateKey = encryptor.getPrivateKey();
    }

    @Override
    public boolean startConnection() {
        if (!super.startConnection()) {
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString("secretKey", secretKey);
        navController.navigate(R.id.action_seQRServer_to_severFragment, bundle);
        return true;
    }

    // STEP 1: Send public key to SeQRClient1
    public void sendPublicKey() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("stepCode", 1);
            jsonObject.put("message", publicKey);
            sendQRCode(jsonObject.toString());
            state = CurrentState.STEP2;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // STEP 2: Receive secret key from SeQRClient1, decoding it using private key
    public void receiveSecretKey() {
        receiveQRCode();
    }

    @Override
    public void onQRCodeScan(int stepCode, String result) {
        switch (stepCode) {
            case 2: // STEP 2
                try {
                    secretKey = encryptor.decryptWithPrivateKey(result, privateKey);
                    Toast.makeText(getActivity(), "Secret Key: "+ secretKey, Toast.LENGTH_LONG).show();
                    secret_key_view.setText("Received Secret Key: " + secretKey);
                    state = CurrentState.STEP3;
                }
                catch (Exception e) {
                    Toast.makeText(getActivity(), "Received QR Code is not properly encrypted with public key!", Toast.LENGTH_LONG).show();
                }
                break;
            case 3: // STEP 3
                try {
                    String decodedResult = encryptor.decryptWithSecretKey(result, secretKey);
                    Toast.makeText(getActivity(), "Scanned: "+ decodedResult, Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_se_qrserver, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        public_key_view.setText("Generated Public Key: " + publicKey);

        step1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPublicKey();
            }
        });

        step2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiveSecretKey();
            }
        });
    }
}
