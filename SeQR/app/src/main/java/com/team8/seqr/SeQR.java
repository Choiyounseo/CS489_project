package com.team8.seqr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

public class SeQR extends Fragment {

    protected Button step1_btn;
    protected Button step2_btn;
    protected TextView public_key_view;
    protected TextView secret_key_view;
    protected ImageView qrcodeIv;
    protected NavController navController;

    protected enum CurrentState {
        STEP1,
        STEP2,
        STEP3
    }
    protected CurrentState state = CurrentState.STEP1;

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

        step1_btn = getView().findViewById(R.id.btn_step1);
        step2_btn = getView().findViewById(R.id.btn_step2);
        public_key_view = getView().findViewById(R.id.public_key_view);
        secret_key_view = getView().findViewById(R.id.secret_key_view);
        qrcodeIv = getView().findViewById(R.id.created_qrcode);

        navController = Navigation.findNavController(view);

        Button start_btn = getView().findViewById(R.id.btn_start_connection);

        start_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                startConnection();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
                // TODO
            } else {
                // TODO
                try {
                    JSONObject qrCodeMessage = new JSONObject(result.getContents());
                    onQRCodeScan(qrCodeMessage.getInt("stepCode"), qrCodeMessage.getString("message"));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onQRCodeScan(int stepCode, String result) {

    }

    public boolean startConnection() {
        switch (state) {
            case STEP1:
                Toast.makeText(getActivity(), "Complete Step 1 first!", Toast.LENGTH_LONG).show();
                return false;
            case STEP2:
                Toast.makeText(getActivity(), "Complete Step 2 first!", Toast.LENGTH_LONG).show();
                return false;
            case STEP3:
                return true;
            default:
                return false;
        }
    }

    public void sendQRCode(String message) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(message, BarcodeFormat.QR_CODE,300,300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrcodeIv.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveQRCode() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setOrientationLocked(false); // default가 세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경됩니다.
        integrator.setPrompt("Scanning!");
        integrator.setCameraId(1); // Use front camera to scan
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

}
