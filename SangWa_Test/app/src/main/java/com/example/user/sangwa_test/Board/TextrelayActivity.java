package com.example.user.sangwa_test.Board;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.sangwa_test.R;

public class TextrelayActivity extends AppCompatActivity {
    Button addreply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textrelay);

        addreply = findViewById(R.id.addreply);
        addreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
