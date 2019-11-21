package com.team8.seqr;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

public class encodeFragment extends Fragment {

    private String publicKey;
    private String privateKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_encode, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Show generated public and private key here
        TextView public_key_view = getView().findViewById(R.id.public_key_view);
        TextView private_key_view = getView().findViewById(R.id.private_key_view);

        // Get input and submit event here
        final EditText input_box = getView().findViewById(R.id.input_text);
        Button encode_btn = getView().findViewById(R.id.btn_encode_text);

        // Display results in this view
        final TextView encoded_string_view = getView().findViewById(R.id.encoded_text);
        final TextView decoded_string_view = getView().findViewById(R.id.decoded_text);

        // Generate random public key and private key here
        final Encryptor encryptor = new Encryptor();
        publicKey = encryptor.getPublicKey();
        privateKey = encryptor.getPrivateKey();
        public_key_view.setText("Generated Public Key: " + publicKey);
        private_key_view.setText("Generated Private Key: " + privateKey);

        // Define here what the button will do when clicked
        encode_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                String input = input_box.getText().toString();
                try {
                    String encoded_text = encryptor.encryptWithPublicKey(input, publicKey);
                    String decoded_text = encryptor.decryptWithPrivateKey(encoded_text, privateKey);
                    encoded_string_view.setText("Encoded result: " + encoded_text);
                    decoded_string_view.setText("Decoded result: " + decoded_text);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
