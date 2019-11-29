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

import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONException;
import org.json.JSONObject;

public class SeQRClient extends SeQR {

    private Encryptor encryptor;

    private String publicKey;
    private String secretKey;

    public SeQRClient() {
        encryptor = new Encryptor();

        secretKey = encryptor.getSecretKey();
    }

    @Override
    public boolean startConnection() {
        if (!super.startConnection()) {
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString("secretKey", secretKey);
        navController.navigate(R.id.action_seQRClient_to_clientFragment, bundle);
        return true;
    }

    // STEP 1: Receive public key from SeQRServer1
    public void receivePublicKey() {
        receiveQRCode();
    }

    // STEP 2: Send secret key to SeQRServer1, which is encoded using the public key from STEP 1.
    public void sendSecretKey() {
        try {
            String encryptedSecretKey = encryptor.encryptWithPublicKey(secretKey, publicKey);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("stepCode", 2);
                jsonObject.put("message", encryptedSecretKey);
                sendQRCode(jsonObject.toString());
                state = CurrentState.STEP3;
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // STEP 3: Now feel free to send any QR Code message, encoded with secret key


    @Override
    public void onQRCodeScan(int stepCode, String result) {
        switch (stepCode) {
            case 1: // STEP 1
                publicKey = result;
                Toast.makeText(getActivity(), "Public Key: "+ publicKey, Toast.LENGTH_LONG).show();
                public_key_view.setText("Received Public Key: " + publicKey);
                state = CurrentState.STEP2;
                //sendSecretKey(); // STEP 2
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
        return inflater.inflate(R.layout.fragment_se_qrclient, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        secret_key_view.setText("Generated Secret Key: " + secretKey);

        step1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receivePublicKey();
            }
        });

        step2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSecretKey();
            }
        });
    }

}
