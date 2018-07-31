package com.example.user.sangwa_test.Board;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.sangwa_test.Board.Adapters.BoardReplyAdapter;
import com.example.user.sangwa_test.Board.DTO.BoardReplyDTO;
import com.example.user.sangwa_test.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class BoardTouchActivity extends AppCompatActivity{
    Button toBoardItem, mkreply;
    TextView texttitle, textcontent,textid;
    ListView replyList;
    BoardReplyAdapter adapter;
    ImageView imageView1;

    //이미지로더
    com.nostra13.universalimageloader.core.ImageLoader imageLoader;

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
        imageView1 = findViewById(R.id.imageView1);


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
        String imgRes =data.getStringExtra("imgRes");
        Log.d("터치",id+pw+title+content);
        textid.setText(id);
        texttitle.setText(title);
        textcontent.setText(content);

        //이미지 로딩 메서드
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.blank) // 기본이미지
                .showImageForEmptyUri(R.drawable.blank) // 주소가 없을 경우
                .showImageOnFail(R.drawable.blank)// 실패했을 경우
                .build();

        ImageLoaderConfiguration config =
                new ImageLoaderConfiguration.Builder(getApplicationContext())
                        .defaultDisplayImageOptions(options)
                        .build();
        imageLoader.getInstance().init(config);

        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(imgRes, imageView1, new ImageLoadingListener() {
            //이미지를 불러올 인터넷 상의 이미지 경로,붙여줄 이미지뷰,옵션
            @Override
            public void onLoadingStarted(String s, View view) {
                //이미지 로딩을 시작할때
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                //이미지 로딩을 실패 했을때
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                //이미지 로딩 완료 했을때
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                //이미지 로딩 취소 됬을때
            }
        });
        return;
    }
}
