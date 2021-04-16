package com.example.myfirstapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {

    Button btScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        btScan = findViewById(R.id.bt_scan);

        btScan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        ScanActivity.this
                );

                intentIntegrator.setPrompt("For flash use volume key");

                intentIntegrator.setBeepEnabled(true);

                intentIntegrator.setOrientationLocked(true);

                intentIntegrator.setCaptureActivity(Capture.class);

                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode,data
        );

        if(intentResult.getContents() != null){

            TextView textView = findViewById(R.id.qr_code_result);
            //DO THE QUERY STUFF HERE OR JUST SERVE THE DATA

            //TODO ask the IOTA thing for the data of this bottle
            //TODO change the layout of the page to serve the bottles entire info

            textView.setText(intentResult.getContents());

            Bottle b = getTangleBottleProfile(intentResult.getContents());

            

            AlertDialog.Builder builder = new AlertDialog.Builder(
                    ScanActivity.this
            );

            builder.setTitle("Result");

            builder.setMessage(intentResult.getContents());

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();
        }else{
            Toast.makeText(getApplicationContext(),
                    "OOPS you haven't scanned anything", Toast.LENGTH_SHORT).show();
        }
    }

    private Bottle getTangleBottleProfile(String contents) {

        return new Bottle(1.0,1.0,1.0,1,1,"","","","");
    }
}