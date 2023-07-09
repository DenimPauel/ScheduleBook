package com.example.schedulebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

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

        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mRealm.executeTransaction は、MainActivityの外側(別クラスの世界)なので、long newIDの変数のスコープが届かない。
                //外側で、final宣言した物は、mRealm.executeTransactionの中の世界で使える
                // ヒント transform 'newId' to one element array にしたがって、newIdの型を変更。
                final long[] newId = new long[1];   //long newId;
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Number max = realm.where(Schedule.class).max("id");
                        //newId[0] =0;                //newID=0
                        if ( max != null){
                            newId[0] = max.longValue() + 1; //newID = max.longValue() +1;
                        }
                        Schedule schedule = realm.createObject(Schedule.class, newId[0]);
                        schedule.setDate(new Date());
                        schedule.setTitle("");
                        schedule.setDetail("");
                    }
                });
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                intent.putExtra("ID", newId[0]);
                startActivity(intent);
            }
        });

        //ListViewの一つのcellをタップした時に、InputActivityを起動、編集できるようにする
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //アダプターを取得　ScheduleAdapterへのキャストが必要。
                ScheduleAdapter adapter = (ScheduleAdapter) mListView.getAdapter();
                //セルのポジション=IDを渡す。
                Schedule schedule = adapter.getItem(position);
// ListViewスタイルのInputActivityから、リッチな画面のShowActivityに移行--->>>
//              Intent intent = new Intent(MainActivity.this, InputActivity.class);
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
// ListViewスタイルのInputActivityから、リッチな画面のShowActivityに移行<<<---
                intent.putExtra("ID", schedule.getId());
                startActivity(intent);
            }
        });

        //ListViewの一つのcellをロングタップした時に削除する
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ScheduleAdapter adapter = (ScheduleAdapter) mListView.getAdapter();

                //授業では、schedule は、mRealmクラスの外側なので
                //final Schedule schedule のように、finalの記載が必要といわれるが・・・警告されない。(クラスはfinalの指定いらないのかな？)
                Schedule schedule = adapter.getItem(position);
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        schedule.deleteFromRealm();
                    }
                });
                return true;
            }
        });

    }
}