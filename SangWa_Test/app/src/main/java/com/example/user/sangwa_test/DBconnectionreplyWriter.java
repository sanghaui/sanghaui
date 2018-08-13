package com.example.user.sangwa_test;

import android.os.AsyncTask;
import android.util.Log;

import com.example.user.sangwa_test.Board.DTO.BoardReplyDTO;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;

public class DBconnectionreplyWriter extends AsyncTask<Void,Void,Void>{
    private String inconfig;
    private String postURL;
    private String id;
    private String pw;
    private String title;
    private String content;
    private String date;
    private String reply;
    private String like;
    private String readCount;
    private String imgRes;
    private String encodedImage;
    private int index;

    private String uploadPathA;

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    public void insert(BoardReplyDTO dto){
        this.id = dto.getId();
        this.content = dto.getContent();
        this.index = dto.getParentid();
    }

    @Override
    protected Void doInBackground(Void... Void) {

        inconfig ="http://192.168.0.109:8989";
        postURL=inconfig+"/app/replyInsert";
        //서버에 저장될 이미지 주소 값 설정
        try {
            HttpClient client = new DefaultHttpClient();
            Log.d("접속",postURL);
            HttpPost post = new HttpPost(postURL);
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            //값추가
            params.add(new BasicNameValuePair("id",id));
            params.add(new BasicNameValuePair("index",String.valueOf(index)));
            params.add(new BasicNameValuePair("content",content));
            Log.d("게시판전송","파람설정완료");
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse responsePost = client.execute(post);   //서버로 값을 던짐
            Log.d("게시판전송","데이터전송완료");
        }catch (Exception e){
            e.printStackTrace();
            Log.d("접속","디비커넥션 오류");
        }
        return null;
    }
}
