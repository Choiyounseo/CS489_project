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

import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class severFragment extends Fragment {
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sever, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        Button button_add_content = getView().findViewById(R.id.btn_add_content);

        button_add_content.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // Toast.makeText(getActivity(), "Create Content", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_severFragment_to_createContent);
            }
        });
    }


}
