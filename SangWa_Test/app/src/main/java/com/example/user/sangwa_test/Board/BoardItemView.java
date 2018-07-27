package com.example.user.sangwa_test.Board;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.sangwa_test.R;

public class BoardItemView extends LinearLayout{

    TextView titleText;
    TextView dateText;
    TextView idText;
    ImageView imageView1;
    ProgressBar progressBar;
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
        imageView1=findViewById(R.id.imageView1);
        progressBar=findViewById(R.id.progressBar);

    }

    public  void setTitleText(String titlete){
        titleText.setText(titlete);
    }
    public  void setDateText(String dateTe){
        dateText.setText(dateTe);
    }
    public  void setIdText(String idTe){
        this.idText.setText(idTe);
    }
    public void setImageView1(ImageView imageView1) {
        this.imageView1 = imageView1;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}
