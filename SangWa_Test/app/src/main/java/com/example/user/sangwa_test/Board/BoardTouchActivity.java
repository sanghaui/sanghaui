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
import com.example.user.sangwa_test.Board.DTO.SangWaDTO;
import com.example.user.sangwa_test.DBconnectionLike;
import com.example.user.sangwa_test.DBconnectionLikeCheck;
import com.example.user.sangwa_test.DBconnectionReplyreader;
import com.example.user.sangwa_test.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BoardTouchActivity extends AppCompatActivity {
    Button toBoardItem, mkreply, like_btn;
    TextView texttitle, textcontent, textid;
    ListView replyList;
    BoardReplyAdapter adapter;
    ImageView imageView1;
    ArrayList<BoardReplyDTO> dtolist;
    DBconnectionReplyreader reader;
    String check_like;


    //이미지로더
    com.nostra13.universalimageloader.core.ImageLoader imageLoader;

    private int index;
    private String id;
    private String pw;
    private String title;
    private String content;
    private String date;
    private String reply;
    private String like;
    private int readCount;
    private String imgRes;
    private int likeCh;

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
        like_btn = findViewById(R.id.like_btn);


        final Intent intent = getIntent();
        process(intent);
        //좋아요 확인 처리
        DBconnectionLikeCheck db = new DBconnectionLikeCheck();
        db.insert(index,id);
        try {
        likeCh = db.execute().get();
        if(likeCh==0){
            check_like = "unpressed" ;
        }else{
            check_like = "pressed";
        }
        }catch (Exception e){
            e.printStackTrace();
        }

        //좋아요 버튼 처리

        if (check_like.equals("unpressed")) {
            like_btn.setSelected(false);
        }else{
            like_btn.setSelected(true);
        }


        like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_like.equals("unpressed")) {
                    like_btn.setSelected(true);
                    check_like = "pressed";
                } else {
                    like_btn.setSelected(false);
                    check_like = "unpressed";
                }
            }
        });


        toBoardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBconnectionLike db = new DBconnectionLike();
                SangWaDTO dto = new SangWaDTO();
                dto.setIndex(index);
                dto.setId(id);
                dto.setLikecount(check_like);
                Log.d("터치화면","조회수:"+readCount);
                //조회수 추가 처리
                if (readCount == 0) {
                    dto.setReadCount(1);
                } else {
                    dto.setReadCount(readCount + 1);
                }
                db.insert(dto);
                db.execute();
                finish();
            }
        });

        mkreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reply = new Intent(getApplicationContext(), TextrelayActivity.class);
                reply.putExtra("index", index);
                startActivity(reply);
            }
        });

        replyList = findViewById(R.id.replyList);
        getList();

        replyList.setAdapter(adapter);
    }

    public void process(Intent data) {
        index = data.getIntExtra("index", 0);
        id = data.getStringExtra("id");
        pw = data.getStringExtra("pw");
        title = data.getStringExtra("title");
        content = data.getStringExtra("content");
        imgRes = data.getStringExtra("imgRes");
        readCount = data.getIntExtra("readCount", 0);
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


    public void getList() {
        //DB에서 값 받아옴
        reader = new DBconnectionReplyreader();
        reader.insert(index);   //게시글 인덱스
        try {
            dtolist = reader.execute().get();
            Log.d("반환값", String.valueOf(dtolist.size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //adapter 생성
        adapter = new BoardReplyAdapter();
        Log.d("어뎁터", "생성");

        //배열을 풀어 각각에 리스트에 삽입
        for (int i = 0; i < dtolist.size(); i++) {
            int index = dtolist.get(i).getIndex();
            String id = dtolist.get(i).getId();
            String date = dtolist.get(i).getDate();
            /*String date =dtolist.get(i).getDate();*/
            String content = dtolist.get(i).getContent();
            /*Log.d("게시판글","인덱스:"+index+",제목:"+title+",시간:"+date);*/
            adapter.addItems(new BoardReplyDTO(index, content, id, date));
        }
    }

    @Override
    protected void onResume() {
        getList();
        replyList.setAdapter(adapter);
        super.onResume();
    }
}
