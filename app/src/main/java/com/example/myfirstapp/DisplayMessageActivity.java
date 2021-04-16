package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.SendTransferResponse;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.model.Transfer;
import org.iota.jota.utils.SeedRandomGenerator;
import org.iota.jota.utils.TrytesConverter;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }

    public void sendToTangle(View view){
        System.out.println("Sending to Tangle!");

        IotaAPI api = new IotaAPI.Builder()
                .protocol("https")
                .host("nodes.devnet.iota.org")
                .port(443)
                .build();

        int depth = 3;
        int minimumWeightMagnitude = 9;

        String address = "DANIEQ9JUZZWCZXLWVNTHBDX9G9KZTJP9VEERIIFHY9SIQKYBVAHIMLHXPQVE9IXFDDXNHQINXJDRPFDXNYVAPLZAW";

        String myRandomSeed = SeedRandomGenerator.generateNewSeed();

        int securityLevel = 2;

        String message = TrytesConverter.asciiToTrytes("Hello world");

        String tag = "HELLOWORLD";

        int value = 0;

        Transfer zeroValueTransaction = new Transfer(address, value, message, tag);

        ArrayList<Transfer> transfers = new ArrayList<Transfer>();

        transfers.add(zeroValueTransaction);

        try {
            SendTransferResponse response = api.sendTransfer(myRandomSeed, securityLevel, depth, minimumWeightMagnitude, transfers, null, null, false, false, null);
            System.out.println(response.getTransactions());
        } catch (ArgumentException e) {
            // Handle error
            e.printStackTrace();
        }


    }
}