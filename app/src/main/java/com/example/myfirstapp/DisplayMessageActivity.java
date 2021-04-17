package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.GetBundleResponse;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.model.Transaction;
import org.iota.jota.utils.TrytesConverter;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DisplayMessageActivity extends AppCompatActivity {

    IotaAPI api;

    String tailTransactionHash = "NJGJB9AAYMUJWJQQLFYXGMZGYIOJYLFHYNDHWSTVGOOLBYTB9QJCYYZFADSIFWK9HGEXUWJSDMJYPA999";

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

        //Change thread policy
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        api = new IotaAPI.Builder()
                    .protocol("https")
                    .host("nodes.devnet.iota.org")
                    .port(443)
                    .build();

    }

    public void readFromTangle(View view){
        System.out.println("Reading from Tangle!");

        try {
            GetBundleResponse response = api.getBundle(tailTransactionHash);
            System.out.println(TrytesConverter.trytesToAscii(response.getTransactions().get(0).getSignatureFragments().substring(0,2186)));
        } catch (ArgumentException e) {
            // Handle error
            e.printStackTrace();
        }
    }
}