package com.example.myfirstapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {

    Button btScan;
    String googlemapslink = "";

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


        // Assuming you are using xml layout
        Button button = (Button)findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(googlemapslink));
                startActivity(viewIntent);
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

            //Sets contents of textview
            //textView.setText(intentResult.getContents());

            Bottle b = getTangleBottleProfile(intentResult.getContents());

            //Set all the values

            //Overall score
            TextView score = (TextView)findViewById(R.id.nrScore);
            score.setText("5/5");

            //Comparison
            TextView comparison = (TextView)findViewById(R.id.comparison1);
            comparison.setText("The eco footprint of this bottle equals 20 steaks!");

            //Energy use
            TextView en = (TextView)findViewById(R.id.nrEnergyUse);
            en.setText(Double.toString(b.getEnergy_use()));

            //Recycled Material
            TextView em = (TextView)findViewById(R.id.nrRecycledMat);
            en.setText(Double.toString(b.getRecycled()));

            //Emissions


            //Producer eco-friendlyness


            googlemapslink = getMaps(b);

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

    private String getMaps(Bottle b) {

        String place1 = b.getCity();
        String place2 = b.getSoft_drink_prod();
        String place3 = b.getRetail();

        String maps = "https://www.google.com/maps/dir/"+place1+"/"+place2+"/"+place3;

        return maps;
    }

    private Bottle getTangleBottleProfile(String contents) {
        //Tangle read requests would be here

        return new Bottle(6.02,168.58,70,1,1,"","Moscow","Berlin","Paris");
    }
}