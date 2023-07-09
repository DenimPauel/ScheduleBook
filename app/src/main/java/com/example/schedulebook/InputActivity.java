package com.example.schedulebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import io.realm.Realm;

public class InputActivity extends AppCompatActivity {

    private Realm mRealm;
    private long mId;       //授業では、Longクラスだが、long変数でよいので、先頭小文字に
    private TextView mDate;
    private EditText mTitle;
    private EditText mDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        mRealm = Realm.getDefaultInstance();
        mDate = (TextView) findViewById(R.id.date);
        mTitle = (EditText) findViewById(R.id.title);
        mDetail = (EditText) findViewById(R.id.detail);

        if (getIntent() != null ){
            mId = getIntent().getLongExtra("ID", -1);

            Schedule schedule = mRealm.where(Schedule.class).equalTo("id", -1).findFirst();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String formatDate = sdf.format(schedule.getDate());
            mDate.setText(formatDate);
            mTitle.setText(schedule.getTitle());
            mDate.setText(schedule.getDetail());
        }
    }
}