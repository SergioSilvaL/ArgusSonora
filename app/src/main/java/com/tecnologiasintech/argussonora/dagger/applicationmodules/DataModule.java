package com.tecnologiasintech.argussonora.dagger.applicationmodules;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.tecnologiasintech.argussonora.data.DataRepository;
import com.tecnologiasintech.argussonora.data.IDataRepository;
import com.tecnologiasintech.argussonora.data.local.IUserPrefs;
import com.tecnologiasintech.argussonora.data.local.UserPrefs;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    IUserPrefs provideUserPrefs(Context context) {
        return new UserPrefs(context);
    }

    @Provides
    @Singleton
    IDataRepository provideDataRepository(IUserPrefs sharedPreferences, DatabaseReference databaseReference){
        return new DataRepository(sharedPreferences, databaseReference);
    }
}
