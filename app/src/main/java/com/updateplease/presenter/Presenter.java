package com.updateplease.presenter;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
