package com.beancopy;

public interface CopyCallback {

    void OnSuccess(Object s, Object t);

    void onFailure(Object s, Object t, Exception e);
}
