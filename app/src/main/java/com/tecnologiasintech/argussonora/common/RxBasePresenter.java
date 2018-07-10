package com.tecnologiasintech.argussonora.common;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public abstract class RxBasePresenter implements BaseViewPresenterContract.Presenter {

    private CompositeDisposable subscriptions = new CompositeDisposable();

    public void subscribe(Disposable subscription) {
        subscriptions.add(subscription);
    }

    public void unSubscribe() {
        subscriptions.clear();
    }

}