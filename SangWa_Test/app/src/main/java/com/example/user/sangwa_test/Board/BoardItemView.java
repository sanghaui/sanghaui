package com.example.user.sangwa_test.Board;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.sangwa_test.R;

public class BoardItemView extends LinearLayout{

    TextView titleText;
    TextView dateText;
    TextView idText;

    public BoardItemView(Context context) {
        super(context);
        init(context);
    }

    public BoardItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater boardVIew = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        boardVIew.inflate(R.layout.board_item,this,true);

        titleText=findViewById(R.id.titleText);
        dateText=findViewById(R.id.dateText);
        idText=findViewById(R.id.idText);

    }

    public  void setTitleText(String title){
        titleText.setText(title);
    }
    public  void setDateText(String content){
        dateText.setText(content);
    }
    public  void setIdText(String writer){
        idText.setText(writer);
    }


}
