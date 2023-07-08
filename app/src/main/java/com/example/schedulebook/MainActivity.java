package com.example.schedulebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm mRealm;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRealm = Realm.getDefaultInstance();
        mListView = (ListView) findViewById(R.id.listView);

        //RealmObjectのSchedules を
        RealmResults<Schedule> schedules = mRealm.where(Schedule.class).findAll();
        // ScheduleAdapter クラスのインスタンス：adapterを作成
        ScheduleAdapter adapter = new ScheduleAdapter(schedules);
        // mListViewにadapterを渡す。場所を指示するだけで、個々のセルの描画は、ここでは気にしない。
        mListView.setAdapter(adapter);


        Button dbTest = (Button) findViewById(R.id.db_test_button);
        dbTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, RealmTestActivity.class);
                startActivity(intent);
            }
        });
    }
}