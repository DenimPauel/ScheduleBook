package com.example.schedulebook;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ScheduleBookApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        // FATAL EXCEPTION: main
        // Process: com.example.schedulebook, PID: 8598
        // io.realm.exceptions.RealmException: Running transactions on the UI thread has been disabled.
        // It can be enabled by setting 'RealmConfiguration.Builder.allowWritesOnUiThread(true)'.
        //【Android】Realm10.0にアップデート後RealmExceptionで落ちてしまう場合の暫定的な対応方法
        // https://qiita.com/YuukiYoshida/items/62a2203efea97eea2115
        // Realm 10.0以上では、UIスレッド上での トランザクション処理は禁止になったらしく
        // UIスレッドで実行させるためには、以下のコードが必要(ただしこれは回避策)
        RealmConfiguration config = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        Realm.setDefaultConfiguration(config);

    }
}
