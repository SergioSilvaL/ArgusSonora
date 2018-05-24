package com.tecnologiasintech.argussonora.common;

public interface BaseViewPresenterContract {

    interface View {
        void showProgressBar();

        void dismissProgressBar();
    }

    interface Presenter {
        // TODO: subscribe

        // TODO: unSubscribe
    }
}
