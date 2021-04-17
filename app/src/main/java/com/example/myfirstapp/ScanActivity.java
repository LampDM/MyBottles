package com.example.myfirstapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.GetBundleResponse;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.utils.TrytesConverter;

public class ScanActivity extends AppCompatActivity {

    Button btScan;
    String googlemapslink = "";

    IotaAPI api;

    String tailTransactionHash = "9CMSHXOZWYHPYQNAMPBFAEWQFXYXCYQRCGHOOINGHYE9WQHKWVVOVG9RBQMKFVVWSE9SAEJHDYTMVB999";

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

        //Change thread policy
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        api = new IotaAPI.Builder()
                .protocol("https")
                .host("nodes.devnet.iota.org")
                .port(443)
                .build();
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
            em.setText(Double.toString(b.getRecycled()));

            //Emissions
            TextView emis = (TextView)findViewById(R.id.nrEmissions);
            emis.setText(Double.toString(b.getEmissions()));

            //Producer eco-friendlyness
            TextView ecof = (TextView)findViewById(R.id.nrEcoProducer);
            ecof.setText(Double.toString(b.getEcof()));

            googlemapslink = getMaps(b);

            AlertDialog.Builder builder = new AlertDialog.Builder(
                    ScanActivity.this
            );

            builder.setTitle("Result");

            builder.setMessage(tailTransactionHash);

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

        System.out.println("Reading from Tangle!");
        String tangledata = "";
        try {
            GetBundleResponse response = api.getBundle(tailTransactionHash);
            tangledata = TrytesConverter.trytesToAscii(response.getTransactions().get(0).getSignatureFragments().substring(0,2186));
        } catch (ArgumentException e) {
            // Handle error
            e.printStackTrace();
        }
        System.out.println(tangledata);
        String[] td = tangledata.split(";");

        return new Bottle(Double.parseDouble(td[0]),Double.parseDouble(td[1]),Double.parseDouble(td[2]),Integer.parseInt(td[3]),Integer.parseInt(td[4]),td[5],"Moscow","Paris","Rome");
    }
}