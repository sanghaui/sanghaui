package com.example.user.sangwa_test;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DBconnectionLikeCheck extends AsyncTask<Void,Void,Integer>{
    private String inconfig;
    private String postURL;
    private String id;
    private String pw;
    private String title;
    private String content;
    private String date;
    private String reply;
    private int like;
    private int readCount;
    private String imgRes;
    private String encodedImage;
    private int index;
    private int succ;


    private String uploadPathA;

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    public void insert(int index,String id ){
        this.index = index;
        this.id = id;
    }

    @Override
    protected Integer doInBackground(Void... Void) {
            Log.d("데이터값",id);
        inconfig ="http://192.168.0.109:8989";
        postURL=inconfig+"/app/likeReadCheck";
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(postURL);
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            //값추가
            params.add(new BasicNameValuePair("index",String.valueOf(index)));
            params.add(new BasicNameValuePair("id",id));
            Log.d("게시판전송","like:"+like+"readCount:"+readCount);
            Log.d("게시판전송","파람설정완료");
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse responsePost = client.execute(post);   //서버로 값을 던짐
            Log.d("게시판전송","데이터전송완료");

            //데이터 받아옴
            InputStream is = responsePost.getEntity().getContent();
            Document doc = parseXML(is);

            NodeList descNodes = doc.getElementsByTagName("list");

            for (int i = 0; i < descNodes.getLength(); i++) {
                String id = "", date = "", image = "";
                for (Node node = descNodes.item(i).getFirstChild();
                     node != null;
                     node = node.getNextSibling()) {

                    //첫번째 자식을 시작으로 마지막까지 다음 형제를 실행
                    if (node.getNodeName().equals("state")) {
                        succ = Integer.parseInt(node.getTextContent());
                    }
                }
            }
            /*Log.d("Sub1", "" + sangWaDTOArrayList.size());*/

        }catch (Exception e){
            e.printStackTrace();
            Log.d("접속","디비커넥션 오류");
        }

        return succ;
    }

    private Document parseXML(InputStream stream) throws Exception {
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;

        try {
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
            doc = objDocumentBuilder.parse(stream);
        } catch (Exception ex) {
        }
        return doc;
    }
}
