package com.example.home.sample;

import android.os.AsyncTask;
import android.util.Log;

import com.example.home.sample.Board.SangWaDTO;

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

import static java.net.Proxy.Type.HTTP;

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
    /*BoardAdapter adapter = new BoardAdapter();*/

    ArrayList<SangWaDTO> sangWaDTOArrayList = new ArrayList<>();

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
    protected ArrayList<SangWaDTO> doInBackground(Void... Void) {
        inconfig ="http://192.168.0.109:8989";
        postURL=inconfig+"/app/anAllList";
        try {
            HttpClient client = new DefaultHttpClient();
            Log.d("접속",postURL);
            HttpPost post = new HttpPost(postURL);
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, org.apache.http.protocol.HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse responsePost = client.execute(post);   //서버로 값을 던짐
            Log.d("접속","데이터전송완료");

            //데이터 받아옴
            InputStream is = responsePost.getEntity().getContent();
            Document doc = parseXML(is);

            NodeList descNodes = doc.getElementsByTagName("list");

            for (int i = 0; i < descNodes.getLength(); i++) {
                String id = "", name = "", date = "", image = "", content = "";
                int index = 0;
                for (Node node = descNodes.item(i).getFirstChild();
                     node != null;
                     node = node.getNextSibling()) {

                    //첫번째 자식을 시작으로 마지막까지 다음 형제를 실행
                    if (node.getNodeName().equals("b_id")) {
                        id = node.getTextContent();
                    } else if (node.getNodeName().equals("b_title")) {
                        title = node.getTextContent();
                    } else if (node.getNodeName().equals("b_date")) {
                        date = node.getTextContent();
                    } else if (node.getNodeName().equals("imgRes")) {
                        imgRes = node.getTextContent();
                    } else if (node.getNodeName().equals("b_content")) {
                        content = node.getTextContent();
                    } else if (node.getNodeName().equals("b_index")) {
                        index = Integer.parseInt(node.getTextContent());
                    }
                        Log.d("DB에서받은값","title:"+title+", id:"+id+", date:"+date + ", imgRes : " + imgRes + ", content : " + content + ", index : " + index);
                }
                if (!id.equals("")) {
                    sangWaDTOArrayList.add(new SangWaDTO(id, title, date,imgRes,content,index));
                    /*adapter.addItems(new SangWaDTO(id, name, date));*/
                }
            }
            /*Log.d("Sub1", "" + sangWaDTOArrayList.size());*/

        }catch (Exception e){
            e.printStackTrace();
            Log.d("접속","디비커넥션 오류");
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
            //Log.d("BB", ex.getMessage());
        }
        return doc;
    }
}
