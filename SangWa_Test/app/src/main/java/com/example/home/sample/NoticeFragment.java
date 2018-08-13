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
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.home.sample.Board.BoardTouchActivity;
import com.example.home.sample.Board.BoardWriter;
import com.example.home.sample.Board.SangWaDTO;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NoticeFragment extends Fragment {

    /*Button button1;*/
    ListView listView;
    NotifyAdapter adapter;
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
        ViewGroup noticefrag = (ViewGroup) inflater.inflate(R.layout.noticefragment, container,false);

        getList();

        //리스트 할당
        listView = noticefrag.findViewById(R.id.listView);

        listView.setAdapter(adapter);

        //글쓰기버튼
        /*button1 = noticefrag.findViewById(R.id.button1);*/

        //글쓰기
        /*button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BoardWriter.class);
                startActivity(intent);
            }
        });
*/
        //리스트 선택 처리


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SangWaDTO dto = (SangWaDTO) adapter.getItem(position);
                Intent boardTouch = new Intent(getContext(), BoardTouchActivity.class);
                boardTouch.putExtra("tag", "touch");
                boardTouch.putExtra("id",dto.getId());
                boardTouch.putExtra("content",dto.getContent());
                boardTouch.putExtra("title",dto.getTitle());
                boardTouch.putExtra("date",dto.getDate());
                startActivity(boardTouch);
            }
        });

        return noticefrag;
    }

    @Override
    public void onResume() {
        getList();
        Log.d("재시작","리스트 다시 불러옴");
        listView.setAdapter(adapter);
        super.onResume();
    }

    public void getList(){
        //DB에서 값 받아옴
        DBconnectionNoticereader reader = new DBconnectionNoticereader();

        try {
            dtolist=reader.execute().get();
            Log.d("반환값", String.valueOf(dtolist.size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //adapter 생성
        adapter = new NotifyAdapter();

        //배열을 풀어 각각에 리스트에 삽입
        for(int i = 0 ; i < dtolist.size() ; i++){
            Log.d("공지사항", String.valueOf(dtolist.size()));
            int index = dtolist.get(i).getIndex();
            String id = dtolist.get(i).getId();
            String title = dtolist.get(i).getTitle();
            String date =dtolist.get(i).getDate();
            String content = dtolist.get(i).getContent();
            String readcount = dtolist.get(i).getReadCount();
            Log.d("공지사항","아이디:"+id+",제목:"+title+",시간:"+date+",내용"+content);
            adapter.addItems(new SangWaDTO(index,id,title,date,content,readcount));
        }
    }







}