package com.example.user.sangwa_test.Board;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.sangwa_test.Board.Adapters.BoardReplyAdapter;
import com.example.user.sangwa_test.Board.DTO.BoardReplyDTO;
import com.example.user.sangwa_test.R;

public class BoardTouchActivity extends AppCompatActivity{
    Button toBoardItem, mkreply;
    TextView texttitle, textcontent,textid;
    ListView replyList;
    BoardReplyAdapter adapter;

    private String id;
    private String pw;
    private String title;
    private String content;
    private String date;
    private String reply;
    private String like;
    private int readCount;
    private String imgRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_touch);

        toBoardItem = findViewById(R.id.toBoardItem);
        textid = findViewById(R.id.textid);
        texttitle = findViewById(R.id.texttitle);
        textcontent = findViewById(R.id.textcontent);
        mkreply = findViewById(R.id.mkreply);

        Intent intent = getIntent();
        process(intent);

        toBoardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mkreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),TextrelayActivity.class);
                startActivity(intent1);
            }
        });

        replyList = findViewById(R.id.replyList);
        adapter = new BoardReplyAdapter();
        adapter.addItems(new BoardReplyDTO("제목입니다","내용입니다","아이디입니다"));
        adapter.addItems(new BoardReplyDTO("제목입니다","내용입니다","아이디입니다"));
        adapter.addItems(new BoardReplyDTO("제목입니다","내용입니다","아이디입니다"));
        adapter.addItems(new BoardReplyDTO("제목입니다","내용입니다","아이디입니다"));
        adapter.addItems(new BoardReplyDTO("제목입니다","내용입니다","아이디입니다"));

        replyList.setAdapter(adapter);
    }

    public void process(Intent data){

        String id =data.getStringExtra("id");
        String pw =data.getStringExtra("pw");
        String title =data.getStringExtra("title");
        String content =data.getStringExtra("content");
        Log.d("터치",id+pw+title+content);
        textid.setText(id);
        texttitle.setText(title);
        textcontent.setText(content);
    }
}
