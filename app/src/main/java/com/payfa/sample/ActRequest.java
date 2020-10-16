package com.payfa.sample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.payfa.Listeners.PayfaRequest;
import com.payfa.Payfa;
import com.payfa.enums.Currency;
import com.payfa.models.ErrorModel;
import com.payfa.network.ClientConfigs;

import java.util.Random;

public class ActRequest extends AppCompatActivity implements PayfaRequest {

    private TextView textView;
    private Button btnRequest;
    private static final String API = "f706b207cc5bd388ec604817b24a7ae7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_request);

        textView = findViewById(R.id.textView);
        btnRequest = findViewById(R.id.btnRequest);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Payfa().init(API, ActRequest.this)
                        .amount(1100, Currency.toman)
                        .invoice(new Random().nextInt(1256325))
                        .listener(ActRequest.this)
                        .internalBrowser(false)
                        .requestCode(1371)
                        .build();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("TextProject", "resultCode   " + resultCode);
        Log.i("TextProject", "requestCode   " + requestCode);
    }

    @Override
    public void onLaunch() {
        Log.i("TextProject", "onLaunch");
    }

    @Override
    public void onFailure(ErrorModel errorModel) {
        Log.i("TextProject", errorModel.msg + "   " + errorModel.code);
        textView.setText(errorModel.msg);
    }

    @Override
    public void onFinish(String paymentId) {
        Log.i("TextProject", "onFinish = " + paymentId);
        textView.setText(paymentId);
    }

}