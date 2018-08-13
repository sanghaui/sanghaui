package com.example.home.sample.Board;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.home.sample.Board.DTO.BoardReplyDTO;
import com.example.home.sample.DBconnectionreplyWriter;
import com.example.home.sample.R;

public class TextrelayActivity extends AppCompatActivity {
    Button addreply;
    EditText replycontent;
    BoardReplyDTO dto = new BoardReplyDTO();
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textrelay);

        Intent intent = getIntent();
        index = intent.getIntExtra("index",0);
        Log.d("부모인덱스1",String.valueOf(index));
        replycontent = findViewById(R.id.replycontent);

        addreply = findViewById(R.id.addreply);
        addreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBconnectionreplyWriter reply = new DBconnectionreplyWriter();
                dto.setContent(replycontent.getText().toString());
                dto.setId("뎃글러");
                dto.setParentid(index);
        Log.d("부모인덱스2",String.valueOf(index));
                reply.insert(dto);
                reply.execute();
                finish();
            }
        });

    }
}
