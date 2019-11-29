package com.team8.seqr;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class clientFragment extends Fragment {

    private String secretKey;
    private Encryptor encryptor;

    public clientFragment() {
        encryptor = new Encryptor();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_client, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        secretKey = getArguments().getString("secretKey");
        Toast.makeText(getActivity(), "Secret Key: "+ secretKey, Toast.LENGTH_LONG).show();

        Button scan_btn = getView().findViewById(R.id.btn_initiate_scan);

        scan_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                receiveQRCode();
            }
        });
    }

    public void receiveQRCode() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setOrientationLocked(false); // default가 세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경됩니다.
        integrator.setPrompt("Scanning!");
        integrator.setCameraId(1); // Use front camera to scan
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
                // TODO
            } else {
                String encodedMessage = result.getContents();
                Toast.makeText(getActivity(), "Scanned: "+ encodedMessage, Toast.LENGTH_LONG).show();
                // TODO
                TextView result_view = getView().findViewById(R.id.qr_code_result_view);
                try {
                    String decodedMessage = encryptor.decryptWithSecretKey(encodedMessage, secretKey);
                    result_view.setText("Result: " + decodedMessage);
                }
                catch (Exception e) {
                    Toast.makeText(getActivity(), "Received QR Code is not properly encrypted!", Toast.LENGTH_LONG).show();
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
