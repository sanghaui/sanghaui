package com.example.user.sangwa_test.Board;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.sangwa_test.R;

public class NoticeTouchActivity extends AppCompatActivity {
    Button backkey;
    TextView textcontent, textid, textdate, textnotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_touch);

        textcontent =findViewById(R.id.textcontent);
        textid =findViewById(R.id.textid);
        textdate =findViewById(R.id.textdate);
        textnotice =findViewById(R.id.textnotice);


        Intent intent = getIntent();
        String title=intent.getStringExtra("title");
        textcontent.setText(title);
        backkey = findViewById(R.id.backkey);
        backkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
