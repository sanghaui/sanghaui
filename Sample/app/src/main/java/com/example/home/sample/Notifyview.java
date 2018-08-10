package com.example.home.sample;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Notifyview extends LinearLayout{
    ImageView imageView;
    TextView title, user;


    public Notifyview(Context context) {
        super(context);
        init(context);
    }

    public Notifyview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.notifyview, this, true);

        title = findViewById(R.id.title);
        user = findViewById(R.id.user);
        imageView = findViewById(R.id.imageView);


    }
    public void setTitle(String tit) {title.setText(tit); }
    public void setId(String id) { user.setText(id);}
    public void setImage(int resId){
        imageView.setImageResource(resId);//이미지뷰
    }
}
