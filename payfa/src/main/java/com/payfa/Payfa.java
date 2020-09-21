package com.payfa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.payfa.Listeners.PayfaInterface;
import com.payfa.enums.Currency;
import com.payfa.models.RequestType;
import com.payfa.models.Status;
import com.payfa.models.ErrorModel;
import com.payfa.network.ClientConfigs;
import com.payfa.network.PayfaService;
import com.payfa.sample.ErrorMessage;

import org.jetbrains.annotations.NotNull;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Payfa {

    private Activity activity;
    private Currency currency;
    private int amount;
    private int requestCode;
    private int invoice_id;
    private PayfaInterface listen;
    private String api;
    private String name;
    private String details;
    private boolean internalBrowser;
    private String toolbarColor = "#43377e";

    public Payfa init(@NotNull Activity activity, @NotNull String api) {
        this.activity = activity;
        this.api = api;
        return this;
    }

    public Payfa amount(int amount, @NotNull Currency currency) {
        this.currency = currency;
        switch (currency) {
            case rial:
                this.amount = amount;
                break;
            case toman:
                this.amount = amount * 10;
                break;
        }
        return this;
    }

    public Payfa invoice(int invoice_id) {
        this.invoice_id = invoice_id;
        return this;
    }

    public Payfa nameAndDetails(String name, String details) {
        this.name = name;
        this.details = details;
        return this;
    }

    public Payfa listener(@NotNull PayfaInterface listen) {
        this.listen = listen;
        return this;
    }

    public Payfa requestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public Payfa internalBrowser(boolean internalBrowser) {
        this.internalBrowser = internalBrowser;
        return this;
    }

    public Payfa toolbarColor(String toolbarColor) {
        this.toolbarColor = toolbarColor;
        return this;
    }

    public void build() {
        if (listen == null) {
            return;
        }
        listen.onLaunch();
        if (activity == null) {
            listen.onFailure(new ErrorModel(ErrorMessage.msg701[0], ErrorMessage.msg701[1]));
            return;
        }
        if (api == null || api.equals("")) {
            listen.onFailure(new ErrorModel(ErrorMessage.msg703[0], ErrorMessage.msg703[1]));
            return;
        }
        if (amount <= 10000) {
            listen.onFailure(new ErrorModel(ErrorMessage.msg702[0], ErrorMessage.msg702[1]));
            return;
        }

        RequestType requestType = new RequestType();
        requestType.invoice_id = invoice_id;
        requestType.amount = amount;
        if (name != null) {
            requestType.name = name;
        }
        if (details != null) {
            requestType.details = details;
        }
        requestType.callback = ClientConfigs.callback();
        requestType.api = api;
        requestType.packageName = activity.getPackageName();
        getData(requestType);
    }

    private void getData(RequestType requestType) {
        Call<Status> call = new PayfaService(activity).getApiInterface().getData(requestType.api, requestType.amount, requestType.invoice_id, requestType.callback);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(@NonNull Call<Status> call, @NonNull Response<Status> response) {
                if (response.isSuccessful()) {
                    Status data = response.body();
                    if (data != null) {
                        if (Integer.parseInt(data.status) > 0) {
                            listen.onFinish();
                            if (internalBrowser) {
                                openPayfaWithCustomTab(data.status);
                            } else {
                                openPayfaWithChrome(data.status);
                            }
                        } else {
                            listen.onFailure(new ErrorModel(data.status, data.msg));
                        }
                    } else {
                        listen.onFailure(new ErrorModel(ErrorMessage.msg700[0], ErrorMessage.msg700[1]));
                    }
                } else {
                    listen.onFailure(new ErrorModel(ErrorMessage.msg700[0], ErrorMessage.msg700[1]));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Status> call, @NonNull Throwable t) {
                listen.onFailure(new ErrorModel(ErrorMessage.msg700[0], ErrorMessage.msg700[1]));
            }
        });
    }

    private void openPayfaWithCustomTab(String status) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent cti = builder
                .setToolbarColor(Color.parseColor(toolbarColor))
                .setShowTitle(true)
                .build();
        cti.intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        cti.intent.setPackage("com.android.chrome");
        cti.intent.setData(Uri.parse(ClientConfigs.gateway() + status));
        if (requestCode != 0) {
            activity.startActivityForResult(cti.intent, requestCode);
        } else {
            activity.startActivity(cti.intent);
        }
    }

    private void openPayfaWithChrome(String status) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ClientConfigs.gateway() + status));
        intent.setPackage("com.android.chrome");
        if (requestCode != 0) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivity(intent);
        }
    }
}
