package com.beancopy;

public interface CopyCallback<S, T> {

    void OnSuccess(S s, T t);

    void onFailure(S s, T t, Exception e);
}
