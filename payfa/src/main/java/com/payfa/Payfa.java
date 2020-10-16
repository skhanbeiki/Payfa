package com.payfa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;

import com.google.gson.Gson;
import com.payfa.Listeners.PayfaRequest;
import com.payfa.Listeners.PayfaVerify;
import com.payfa.enums.Currency;
import com.payfa.models.RequestType;
import com.payfa.models.Status;
import com.payfa.models.ErrorModel;
import com.payfa.models.VerifyType;
import com.payfa.network.ClientConfigs;
import com.payfa.network.PayfaService;
import com.payfa.sample.ErrorMessage;
import com.payfa.sample.PreferencesHelper;

import org.jetbrains.annotations.NotNull;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Payfa {

    private Activity activity;
    private int amount;
    private int requestCode;
    private int invoice_id;
    private PayfaRequest payfaRequest;
    private PayfaVerify payfaVerify;
    private String api;
    private String name;
    private String details;
    private boolean internalBrowser;
    private String toolbarColor = "#43377e";
    private final String preferenceName = "PayfaStatus";

    public void payStatus(@NotNull String api, String payment_id, @NotNull PayfaVerify listen) {
        this.payfaVerify = listen;
        if (payfaVerify == null) {
            return;
        }
        if (api == null || api.equals("")) {
            payfaVerify.onFailure(new ErrorModel(ErrorMessage.msg703[0], ErrorMessage.msg703[1]));
            return;
        }
        if (payment_id == null || payment_id.equals("")) {
            payfaVerify.onFailure(new ErrorModel(ErrorMessage.msg704[0], ErrorMessage.msg704[1]));
            return;
        }

        VerifyType verifyType = new VerifyType();
        verifyType.api = api;
        verifyType.payment_id = Integer.parseInt(payment_id);
        verify(verifyType);
    }

    public void payStatus(@NotNull String api, Context context, @NotNull PayfaVerify listen) {
        this.payfaVerify = listen;

        if (payfaVerify == null) {
            return;
        }
        if (api == null || api.equals("")) {
            payfaVerify.onFailure(new ErrorModel(ErrorMessage.msg703[0], ErrorMessage.msg703[1]));
            return;
        }
        String payment_id = PreferencesHelper.readFromPreferences(context, preferenceName, "NONE");
        if (payment_id == null || payment_id.equals("NONE")) {
            payfaVerify.onFailure(new ErrorModel(ErrorMessage.msg704[0], ErrorMessage.msg704[1]));
            return;
        }

        VerifyType verifyType = new VerifyType();
        verifyType.api = api;
        verifyType.payment_id = Integer.parseInt(payment_id);
        verify(verifyType);
    }

    public Payfa() {

    }

    public Payfa init( @NotNull String api,@NotNull Activity activity) {
        this.activity = activity;
        this.api = api;
        return this;
    }

    public Payfa amount(int amount, @NotNull Currency currency) {
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

    public Payfa listener(@NotNull PayfaRequest listen) {
        this.payfaRequest = listen;
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
        if (payfaRequest == null) {
            return;
        }
        payfaRequest.onLaunch();
        if (activity == null) {
            payfaRequest.onFailure(new ErrorModel(ErrorMessage.msg701[0], ErrorMessage.msg701[1]));
            return;
        }
        if (api == null || api.equals("")) {
            payfaRequest.onFailure(new ErrorModel(ErrorMessage.msg703[0], ErrorMessage.msg703[1]));
            return;
        }
        if (amount <= 10000) {
            payfaRequest.onFailure(new ErrorModel(ErrorMessage.msg702[0], ErrorMessage.msg702[1]));
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
        requestType.callback = activity.getPackageName();
        requestType.api = api;
        request(requestType);
    }

    private void request(RequestType requestType) {
        Call<Status> call = new PayfaService().getApiInterface().request(requestType.api,
                requestType.amount, requestType.invoice_id, requestType.callback, requestType.name,
                requestType.details);
        PreferencesHelper.clearToPreferences(activity, preferenceName);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(@NonNull Call<Status> call, @NonNull Response<Status> response) {
                if (response.isSuccessful()) {
                    Status data = response.body();
                    if (data != null) {
                        if (Integer.parseInt(data.status) > 0) {
                            payfaRequest.onFinish(data.status);
                            if (internalBrowser) {
                                openPayfaWithCustomTab(data.status);
                                PreferencesHelper.saveToPreferences(activity, preferenceName, data.status);
                            } else {
                                openPayfaWithChrome(data.status);
                            }
                        } else {
                            payfaRequest.onFailure(new ErrorModel(data.status, data.msg));
                        }
                    } else {
                        payfaRequest.onFailure(new ErrorModel(ErrorMessage.msg700[0], ErrorMessage.msg700[1]));
                    }
                } else {
                    payfaRequest.onFailure(new ErrorModel(ErrorMessage.msg700[0], ErrorMessage.msg700[1]));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Status> call, @NonNull Throwable t) {
                payfaRequest.onFailure(new ErrorModel(ErrorMessage.msg700[0], ErrorMessage.msg700[1]));
            }
        });
    }

    private void verify(VerifyType verifyType) {
        Call<Status> call = new PayfaService().getApiInterface().verify(verifyType.api, verifyType.payment_id);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(@NonNull Call<Status> call, @NonNull Response<Status> response) {
                if (response.isSuccessful()) {
                    Status data = response.body();
                    if (data != null) {
                        if (Integer.parseInt(data.status) > 0) {
                            payfaVerify.onFinish(data);
                        } else {
                            payfaVerify.onFailure(new ErrorModel(data.status, data.msg));
                        }
                    } else {
                        payfaVerify.onFailure(new ErrorModel(ErrorMessage.msg700[0], ErrorMessage.msg700[1]));
                    }
                } else {
                    payfaVerify.onFailure(new ErrorModel(ErrorMessage.msg700[0], ErrorMessage.msg700[1]));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Status> call, @NonNull Throwable t) {
                payfaVerify.onFailure(new ErrorModel(ErrorMessage.msg700[0], ErrorMessage.msg700[1]));
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
