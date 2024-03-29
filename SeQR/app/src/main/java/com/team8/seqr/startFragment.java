package com.team8.seqr;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Button;

import com.google.zxing.integration.android.IntentIntegrator;


public class startFragment extends Fragment {


    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_start, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        Button button_snd = getView().findViewById(R.id.btn_snd);
        Button button_rcv = getView().findViewById(R.id.btn_rcv);
        ImageButton encode_btn = getView().findViewById(R.id.btn_encode);

        // Click event for Sender
        button_snd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getActivity(), "Sender Selected", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_startFragment_to_seQRServer);
            }
        });

        // Click event for Receiver
        button_rcv.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getActivity(), "Receiver Selected", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_startFragment_to_seQRClient);
            }
        });


        encode_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                navController.navigate(R.id.action_startFragment_to_encodeFragment);
            }
        });




    }



}
