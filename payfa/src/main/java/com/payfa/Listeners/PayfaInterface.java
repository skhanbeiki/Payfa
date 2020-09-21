package com.payfa.Listeners;

import com.payfa.models.ErrorModel;

public interface PayfaInterface {
    void onLaunch();
    void onFailure(ErrorModel errorModel);
    void onFinish();
}
