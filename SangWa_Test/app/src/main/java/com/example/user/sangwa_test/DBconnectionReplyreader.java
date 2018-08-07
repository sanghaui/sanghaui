package com.example.user.sangwa_test;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.user.sangwa_test.Board.DTO.BoardReplyDTO;
import com.example.user.sangwa_test.Board.DTO.SangWaDTO;

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

public class DBconnectionReplyreader extends AsyncTask<Void, Void, ArrayList<BoardReplyDTO>> {
    private String inconfig;
    private String postURL;
    private String id;
    private String pw;
    private String title;
    private String content;
    private String date;
    private int index;
    private int parent;
    private Context context;

    /*BoardAdapter adapter = new BoardAdapter();*/

   /* public void setContext(Context context){
     this.context = context;
    }*/

    public void insert(int index) {
        parent= index;
        Log.d("값설정",String.valueOf(parent));
    }

    ArrayList<BoardReplyDTO> BoardReplyDTOArrayList = new ArrayList<>();

    @Override
    protected void onPreExecute() {

        Log.d("값설정",String.valueOf(parent));
        super.onPreExecute();
    }

    @Override
    protected ArrayList<BoardReplyDTO> doInBackground(Void... Void) {
        inconfig = "http://192.168.0.109:8989";
        postURL = inconfig + "/app/anReplyList";

        try {
            HttpClient client = new DefaultHttpClient();
            Log.d("접속", postURL);
            HttpPost post = new HttpPost(postURL);
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            Log.d("파람설정",String.valueOf(parent));
            params.add(new BasicNameValuePair("parent",String.valueOf(parent)));
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
                    } else if (node.getNodeName().equals("r_id")) {
                        id = node.getTextContent();
                    } else if (node.getNodeName().equals("r_content")) {
                        content = node.getTextContent();
                    }else if (node.getNodeName().equals("r_date")) {
                        date = node.getTextContent();
                    }else if (node.getNodeName().equals("r_parent")) {
                        parent= Integer.parseInt(node.getTextContent());
                    }
                }
                if (!id.equals("")) {
                    Log.d("DB에서받은값", "index:" + index);
                    BoardReplyDTOArrayList.add(new BoardReplyDTO(parent,index,content,id,date));
                    /*adapter.addItems(new SangWaDTO(id, name, date));*/
                }
            }
            /*Log.d("Sub1", "" + sangWaDTOArrayList.size());*/

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("접속", "디비커넥션 오류");
        }

        return BoardReplyDTOArrayList;
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
