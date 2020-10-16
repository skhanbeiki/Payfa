package com.payfa.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.payfa.Listeners.PayfaVerify;
import com.payfa.Payfa;
import com.payfa.models.ErrorModel;
import com.payfa.models.Status;

public class ActVerify extends AppCompatActivity implements PayfaVerify {

    private Button btnVerify;
    private TextView textView;
    private static final String API = "f706b207cc5bd388ec604817b24a7ae7";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_verify);

        textView = findViewById(R.id.textView);
        btnVerify = findViewById(R.id.btnVerify);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TextProject", "onClick");
                new Payfa().payStatus(API, getApplicationContext(), ActVerify.this);
            }
        });

    }

    @Override
    public void onFailure(ErrorModel errorModel) {
        Log.i("TextProject", errorModel.msg);
    }

    @Override
    public void onFinish(Status status) {
        Log.i("TextProject", status.status);
    }
}