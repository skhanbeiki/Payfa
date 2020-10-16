package com.payfa.Listeners;

import com.payfa.models.ErrorModel;
import com.payfa.models.Status;

public interface PayfaVerify {
    void onFailure(ErrorModel errorModel);

    void onFinish(Status status);
}
