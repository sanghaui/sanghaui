package com.example.user.sangwa_test;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.user.sangwa_test.Board.Adapters.BoardAdapter;
import com.example.user.sangwa_test.Board.DTO.SangWaDTO;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DBconnectionreader extends AsyncTask<Void, Void, ArrayList<SangWaDTO>> {
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
    private int index;
    private Context context;

    /*BoardAdapter adapter = new BoardAdapter();*/

   /* public void setContext(Context context){
     this.context = context;
    }*/


    ArrayList<SangWaDTO> sangWaDTOArrayList = new ArrayList<>();

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    /*public void insert(SangWaDTO dto) {
        this.id = dto.getId();
        this.pw = dto.getPw();
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.date = dto.getDate();
        this.reply = dto.getReply();
        this.like = dto.getLike();
        this.readCount = dto.getReadCount();
        this.imgRes = dto.getImgRes();
    }*/

    @Override
    protected ArrayList<SangWaDTO> doInBackground(Void... Void) {
        inconfig = "http://192.168.0.109:8989";
        postURL = inconfig + "/app/anAllList";
        try {
            HttpClient client = new DefaultHttpClient();
            Log.d("접속", postURL);
            HttpPost post = new HttpPost(postURL);
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse responsePost = client.execute(post);   //서버로 값을 던짐
            Log.d("접속", "데이터전송완료");

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
                    if (node.getNodeName().equals("b_index")) {
                        index = Integer.parseInt(node.getTextContent());
                    } else if (node.getNodeName().equals("b_id")) {
                        id = node.getTextContent();
                    }else if (node.getNodeName().equals("b_title")) {
                        title = node.getTextContent();
                    } else if (node.getNodeName().equals("b_content")) {
                        content = node.getTextContent();
                    }else if (node.getNodeName().equals("b_date")) {
                        date = node.getTextContent();
                    }else if (node.getNodeName().equals("b_reply")) {
                        reply = node.getTextContent();
                    }else if (node.getNodeName().equals("b_like")) {
                        like = node.getTextContent();
                    }else if (node.getNodeName().equals("readCount")) {
                        readCount = node.getTextContent();
                    }else if (node.getNodeName().equals("imgRes")) {
                        imgRes = node.getTextContent();
                    }
                }
                if (!id.equals("")) {
                    Log.d("DB에서받은값", "index:" + index);
                    sangWaDTOArrayList.add(new SangWaDTO(index,id,title,content,date,reply,like,readCount,imgRes));
                    /*adapter.addItems(new SangWaDTO(id, name, date));*/
                }
            }
            /*Log.d("Sub1", "" + sangWaDTOArrayList.size());*/

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("접속", "디비커넥션 오류");
        }

        return sangWaDTOArrayList;
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
