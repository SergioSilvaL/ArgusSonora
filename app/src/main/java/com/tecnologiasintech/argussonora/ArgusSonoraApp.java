package com.tecnologiasintech.argussonora;

import android.app.Application;

import com.tecnologiasintech.argussonora.dagger.AppComponent;
import com.tecnologiasintech.argussonora.dagger.DaggerAppComponent;
import com.tecnologiasintech.argussonora.dagger.applicationmodules.AppModule;

public class ArgusSonoraApp extends Application{
    public static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }
}
