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
import android.widget.Button;
import android.app.Fragment.*;
import android.widget.Toast;

public class createInfo extends Fragment {

    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_info, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        Button button_finish = getView().findViewById(R.id.btn_finish);

        button_finish.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getActivity(), "Finish creating information", Toast.LENGTH_SHORT).show();

                String name = getView().findViewById(R.id.tv_name).toString();
                String phone = getView().findViewById(R.id.tv_phone).toString();
                String email = getView().findViewById(R.id.tv_email).toString();

                if (name.isEmpty() || phone.isEmpty() || email.isEmpty()){
                    Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else {

                }


            }
        });
    }



}
