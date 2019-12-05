package com.team8.seqr;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Fragment.*;

import android.widget.Button;
import android.widget.Toast;
import android.widget.ImageView;

import org.json.JSONObject;
import org.json.JSONException;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.preference.EditTextPreference;
import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class createUrl extends Fragment {
    NavController navController;
    private ImageView qrcodeIv;
    private Encryptor encryptor;

    private String secretKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_url, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        encryptor = new Encryptor();
        navController = Navigation.findNavController(view);
        qrcodeIv = getView().findViewById(R.id.created_qrcode);
        Button button_finish = getView().findViewById(R.id.btn_finish);

        final TextInputEditText url_editText = getView().findViewById(R.id.tv_url);

        Bundle args = getArguments();
        secretKey = args.getString("secretKey");
        Toast.makeText(getActivity(), "secretKey is: " + secretKey, Toast.LENGTH_SHORT).show();

        button_finish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String url = url_editText.getText().toString();

                if (url.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show(); // 대신에 editview error option들 사용해도 괜찮을듯..
                }
                else {
                    try {
                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                        String encryptedData = encryptor.encryptWithSecretKey(url, secretKey);
                        try {
                            BitMatrix bitMatrix = multiFormatWriter.encode(encryptedData, BarcodeFormat.QR_CODE,400,400);
                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                            qrcodeIv.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Encryption Failed!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }



}
