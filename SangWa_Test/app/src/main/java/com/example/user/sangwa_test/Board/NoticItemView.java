package com.example.user.sangwa_test.Board;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.sangwa_test.R;

public class NoticItemView extends LinearLayout {
    TextView titleText;
    TextView contentText;
    TextView idText;

    public NoticItemView(Context context) {
        super(context);
        init(context);
    }

    public NoticItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater boardVIew = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        boardVIew.inflate(R.layout.notice_item,this,true);

        titleText=findViewById(R.id.noticeTitleText);
        contentText=findViewById(R.id.noticeContentText);
        idText=findViewById(R.id.noticeIdText);

    }

    public  void setTitleText(String title){
        titleText.setText(title);
    }
    public  void setContentText(String content){
        contentText.setText(content);
    }
    public  void setIdText(String id){
        idText.setText(id);
    }

}
