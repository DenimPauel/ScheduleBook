package com.example.schedulebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.realm.Realm;

public class RealmTestActivity extends AppCompatActivity {

    Realm mRealm;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_test);

        mRealm = Realm.getDefaultInstance();

        mTextView   = (TextView) findViewById(R.id.textView);
        Button create = (Button) findViewById(R.id.create);
        Button read   = (Button) findViewById(R.id.create);
        Button update = (Button) findViewById(R.id.update);
        Button delete = (Button) findViewById(R.id.delete);

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //遷移元に戻る
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}