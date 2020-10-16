package com.payfa.Listeners;

import com.payfa.models.ErrorModel;

public interface PayfaRequest {
    void onLaunch();
    void onFailure(ErrorModel errorModel);
    void onFinish(String paymentId);
}
