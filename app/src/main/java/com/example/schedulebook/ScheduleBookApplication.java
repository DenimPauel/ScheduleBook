package com.example.schedulebook;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ScheduleBookApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
