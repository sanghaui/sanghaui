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

public class DBconnectionDeleter extends AsyncTask<Void,Void,Void>{
    private String inconfig;
    private String postURL;
    private int index;
    private String b_index;

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    public void insert(int index){
        this.index = index;
    }

    @Override
    protected Void doInBackground(Void... Void) {
            Log.d("데이터값",String.valueOf(index));
        b_index = String.valueOf(index);
        inconfig ="http://192.168.0.109:8989";
        postURL=inconfig+"/app/BoardDelete";
        //서버에 저장될 이미지 주소 값 설정
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(postURL);
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            //값추가
            params.add(new BasicNameValuePair("index",b_index));
            Log.d("게시판전송","index:"+b_index);
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
