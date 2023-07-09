package com.example.schedulebook;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.schedulebook.databinding.ActivityShowBinding;

import java.text.SimpleDateFormat;

import io.realm.Realm;

public class ShowActivity extends AppCompatActivity {

    private ActivityShowBinding binding;

    private Realm mRealm;
    private int[] images = {
            R.drawable.image0,  //https://pixabay.com/ から画像を収集。
            R.drawable.image1,
            R.drawable.image2,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShowBinding.inflate(getLayoutInflater());
        View scrollView = binding.getRoot();
        setContentView(scrollView);

        //※授業では、toolbarは、findViewByIdとR.id.toolbar から取得していたが、自動生成されたコードでは、ViewBiningから取得していた。(そのまま使う)
        // Toolbarは、ビューに合わせてスクロールができたり、リッチな表現が可能。
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        //ひな形から削除。toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mRealm = Realm.getDefaultInstance();

        ImageView imageView = (ImageView) findViewById(R.id.toolbar_image);
        TextView date = (TextView) findViewById(R.id.date);
        TextView detail = (TextView) findViewById(R.id.detail);

        if( getIntent() != null){
            long id =getIntent().getLongExtra("ID", -1);

            Schedule schedule = mRealm.where(Schedule.class).equalTo("id", id).findFirst();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            if ( schedule.getDate() != null ) {
                String formatDate = sdf.format(schedule.getDate());
                date.setText(formatDate);
            }
            toolBarLayout.setTitle(schedule.getTitle());
            detail.setText(schedule.getDetail());
            imageView.setImageResource(images[(int) id%images.length]);
        }

        //paletteを使う
        // bitmapを取得、Paletteを生成
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Palette palette = Palette.from(bitmap).generate();
        //paletteから色を取得
        int titleColor  = palette.getLightVibrantColor(Color.WHITE);
        int bodyColor   = palette.getDarkMutedColor(Color.BLACK);
        int scrimColor  = palette.getMutedColor(Color.DKGRAY);
        int iconColor   = palette.getVibrantColor(Color.LTGRAY);
        //ビューに色を設定
        toolBarLayout.setExpandedTitleColor(titleColor);    //タイトル文字色
        toolBarLayout.setContentScrimColor(scrimColor);     //タイトル背景色
        scrollView.setBackgroundColor(bodyColor);           //scrollViewの背景色(これであってる？)
        detail.setTextColor(titleColor);                    //
        fab.setBackgroundTintList(ColorStateList.valueOf(iconColor));//アイコン　valueOfに色を渡して、ColorStateListオブジェクトをを作成
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}