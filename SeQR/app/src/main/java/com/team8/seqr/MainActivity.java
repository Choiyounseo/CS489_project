package com.team8.seqr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.fragment.app.*;


public class MainActivity extends AppCompatActivity {
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    public void sndOnClick(View view){
//        Toast.makeText(this, "Sender selected", Toast.LENGTH_SHORT).show();
//        navController.navigate(R.id.action_startFragment_to_severFragment);

    //}



}
