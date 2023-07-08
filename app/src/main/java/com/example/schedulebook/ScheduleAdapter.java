package com.example.schedulebook;

import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

//                           extends RealmBaseAdapter<Schedule> を入力
public class ScheduleAdapter extends RealmBaseAdapter<Schedule> {
    //右クリック > Generate > Constructor
    public ScheduleAdapter(@Nullable OrderedRealmCollection<Schedule> data) {
        super(data);
    }

    private static class ViewHolder{
        TextView date;
        TextView title;
    }

    //右クリック Generate > Implement Methods >  Select Methods to implement画面から選択
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getViewメソッドは、ListViewのCellが表示される度に呼ばれる
        //第一引数：positionは、何番目のリスト(Cellか)が渡される。
        //第二引数は、CellのViewクラス、ListViewの表示が始めての場合は、数回(画面表示できるcellの個数回)はnull で呼ばれる。
        ViewHolder viewHolder;

        if ( convertView == null ){
                        //LayoutInflater クラス xmlからViewを生成する
            convertView = LayoutInflater.from(parent.getContext())                                 // fromを使ってインスタンスを作成
                    .inflate(android.R.layout.simple_list_item_2, parent, false);                 // inflateメソッドで、xmlからViewを生成する。
            viewHolder = new ViewHolder();                                                          // Viewを束ねたクラス。上で作っている。
            viewHolder.date  = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.title = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(viewHolder);                                                         //TAGには、プログラマが任意のオブジェクトを設定してよい。

        } else {                                                                                    //convertViewが非null(使いまわし)の場合の場合は、
            viewHolder = (ViewHolder) convertView.getTag();                                         //Cell内のTextViewは、解決済み。すでに解決している ViewHolderを取り出す。
        }                                                                                           //こうすることで、高速化とメモリの低消費を実現している。

        Schedule schedule = adapterData.get(position);                                              //モデルであるデータはadapterDataから取得する。ホバーして、ソースを追いかけると、
                                                                                                    //RealmBaseAdapter.Javaで、protected OrderedRealmCollection<T> adapterData;として定義されている。
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String formatDate = sdf.format(schedule.getDate());
        viewHolder.date.setText(formatDate);                    //ここで表示
        viewHolder.title.setText(schedule.getTitle());
        return convertView;
    }
}
