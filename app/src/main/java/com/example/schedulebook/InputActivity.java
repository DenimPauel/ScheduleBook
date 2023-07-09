package com.example.schedulebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

            Schedule schedule = mRealm.where(Schedule.class).equalTo("id", mId).findFirst();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String formatDate = sdf.format(schedule.getDate());
            mDate.setText(formatDate);
            mTitle.setText(schedule.getTitle());
            mDetail.setText(schedule.getDetail());
        }

        //mTitle 文字変更のリスナー
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {  //授業では、final Editable s と書けといわれているが・・・、警告表示がない
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Schedule diary = realm.where(Schedule.class).equalTo("id", mId).findFirst();
                        diary.setTitle( s.toString());
                    }
                });
            }
        });

        //mDetail 文字変更のリスナー
        mDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Schedule diary = realm.where(Schedule.class).equalTo("id", mId).findFirst();
                        diary.setDetail(s.toString());
                    }
                });
            }
        });

    }
}