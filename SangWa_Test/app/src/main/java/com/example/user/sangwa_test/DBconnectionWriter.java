package com.example.user.sangwa_test;

import android.os.AsyncTask;
import android.util.Log;

import com.example.user.sangwa_test.Board.DTO.SangWaDTO;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;

public class DBconnectionWriter extends AsyncTask<Void,Void,Void>{
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

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    public void insert(SangWaDTO dto){
        this.id = dto.getId();
        this.pw = dto.getPw();
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.date = dto.getDate();
        this.reply = dto.getReply();
        this.like = dto.getLike();
        this.readCount = dto.getReadCount();
        this.imgRes = dto.getImgRes();
    }

    @Override
    protected Void doInBackground(Void... Void) {
            Log.d("데이터값",id);
            Log.d("데이터값",pw);
            Log.d("데이터값",content);
            Log.d("데이터값",title);
        inconfig ="http://192.168.0.109:8989";
        postURL=inconfig+"/app/anInsert";
        try {
            HttpClient client = new DefaultHttpClient();
            Log.d("접속",postURL);
            HttpPost post = new HttpPost(postURL);
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            //값추가
            params.add(new BasicNameValuePair("id",id));
            params.add(new BasicNameValuePair("pw",pw));
            params.add(new BasicNameValuePair("content",content));
            params.add(new BasicNameValuePair("title",title));

            Log.d("접속","파람설정완료");
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse responsePost = client.execute(post);   //서버로 값을 던짐
            Log.d("접속","데이터전송완료");
        }catch (Exception e){
            e.printStackTrace();
            Log.d("접속","디비커넥션 오류");
        }



        return null;
    }
}
