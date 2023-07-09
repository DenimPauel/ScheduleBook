package com.example.schedulebook;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

//データベースのモデルクラス
public class Schedule extends RealmObject {

    @PrimaryKey
    private long id;     // 予定を見分けるためのIDが必要 ※これも、privateにして、Setter/Getterをつくってみる。

    //publicで直接的に書くと、更新できない。Setter Getter があると、回避できた。
    private Date date;   //予定の日付
    private String title;    // 予定のタイトル
    private String detail;   // 予定の詳細

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getDetail() {
        return detail;
    }
}
