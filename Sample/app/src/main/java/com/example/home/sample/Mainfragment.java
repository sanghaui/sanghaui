package com.example.home.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.home.sample.Board.BoardTouchActivity;
import com.example.home.sample.Board.SangWaDTO;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Mainfragment extends Fragment {
    ListView listView;
    MainAdapter adapter;
    LinearLayout LL1;
    ArrayList<SangWaDTO> dtolist = new ArrayList<>();
    MainActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup mainfrag = (ViewGroup) inflater.inflate(R.layout.mainfragment,container,false);

        getList();

        listView = mainfrag.findViewById(R.id.listView);
        LL1 = mainfrag.findViewById(R.id.LL1); //메인화면


        listView.setAdapter(adapter);
        //리스트 선택 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SangWaDTO dto = (SangWaDTO) adapter.getItem(position);
                Intent boardTouch = new Intent(getContext(), BoardTouchActivity.class);
                boardTouch.putExtra("tag", "touch");
                boardTouch.putExtra("id", dto.getId());
                boardTouch.putExtra("content", dto.getContent());
                boardTouch.putExtra("title", dto.getTitle());
                boardTouch.putExtra("date", dto.getDate());
                boardTouch.putExtra("imgRes", dto.getImgRes());
                boardTouch.putExtra("index", dto.getIndex());
                startActivity(boardTouch);
            }
        });
        return mainfrag;
    }


    public void getList() {
        //DB에서 값 받아옴
        DBconnectionreader reader = new DBconnectionreader();

        try {
            dtolist = reader.execute().get();
            Log.d("반환값", String.valueOf(dtolist.size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //adapter 생성
        adapter = new MainAdapter();
        String id = "", title = "", date = "", img = "", content = "";
        int index = 0, like = 0;
        int toplike = dtolist.get(0).getLike();
        int topindex = dtolist.get(0).getIndex();
        //배열을 풀어 각각에 리스트에 삽입
        for (int i = 1; i < dtolist.size(); i++) {

        //좋아요가 가장 높은 게시글의 인덱스를 찾음
            if (toplike < dtolist.get(i).getLike()){
                toplike = dtolist.get(i).getLike();
                topindex = dtolist.get(i).getIndex();
            }

            Log.d("메인프레그","아이디:"+id+", 제목:"+title+", 시간:"+date +", 이미지:"+img  +", toplike:" + toplike);
        }
        for (int j = 0; j < dtolist.size(); j++){
            if (dtolist.get(j).getLike() == toplike && dtolist.get(j).getIndex() == topindex){
                id = dtolist.get(j).getId();
                title = dtolist.get(j).getTitle();
                date = dtolist.get(j).getDate();
                img = dtolist.get(j).getImgRes();
                content = dtolist.get(j).getContent();
                like = dtolist.get(j).getLike();
                index = dtolist.get(j).getIndex();
            }
        }
        adapter.addItems(new SangWaDTO(id,title,date,img,content,index, like));

    }




}
