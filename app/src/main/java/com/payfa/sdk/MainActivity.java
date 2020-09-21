package com.payfa.sdk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.payfa.Listeners.PayfaInterface;
import com.payfa.Payfa;
import com.payfa.enums.Currency;
import com.payfa.models.ErrorModel;

public class MainActivity extends AppCompatActivity implements PayfaInterface {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Payfa().init(MainActivity.this, "2b12ab7c2edaf7b9ff376866d997db59")
                        .amount(500000, Currency.rial)
                        .invoice(2140000)
                        .listener(MainActivity.this)
                        .internalBrowser(true)
                        .requestCode(56333)
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
    public void onFinish() {
        Log.i("TextProject", "onFinish");
    }
}