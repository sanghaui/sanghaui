package com.example.user.sangwa_test.Board;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.user.sangwa_test.Board.Adapters.BoardAdapter;
import com.example.user.sangwa_test.Board.DTO.SangWaDTO;
import com.example.user.sangwa_test.DBconnectionreader;
import com.example.user.sangwa_test.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BoardFragment extends Fragment {

    Button writeButton;
    BoardAdapter adapter;
    ListView boardList;
    ArrayList<SangWaDTO> dtolist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup board = (ViewGroup) inflater.inflate(R.layout.board_fragment, container, false);

        getList();

        //리스트 할당
        boardList = board.findViewById(R.id.boardList);
        boardList.setAdapter(adapter);

        //리스트 선택 처리
        boardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SangWaDTO dto = (SangWaDTO) adapter.getItem(position);
                Intent boardTouch = new Intent(getContext(), BoardTouchActivity.class);
                /*boardTouch.putExtra("tag", "touch");*/
                boardTouch.putExtra("id",dto.getId());
                boardTouch.putExtra("pw",dto.getPw());
                boardTouch.putExtra("content",dto.getContent());
                boardTouch.putExtra("title",dto.getTitle());
                boardTouch.putExtra("date",dto.getDate());
                startActivity(boardTouch);
            }
        });

        //글쓰기버튼
        writeButton = board.findViewById(R.id.writeButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writer = new Intent(getContext(), BoardWriter.class);
                startActivity(writer);
            }
        });


        return board;
    }

    @Override
    public void onResume() {
        getList();
        Log.d("재시작","리스트 다시 불러옴");
        boardList.setAdapter(adapter);
        super.onResume();
    }


    public void getList(){
        //DB에서 값 받아옴
        DBconnectionreader reader = new DBconnectionreader();

        try {
            dtolist=reader.execute().get();
            Log.d("반환값", String.valueOf(dtolist.size()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //adapter 생성
        adapter = new BoardAdapter();

        //배열을 풀어 각각에 리스트에 삽입
        for(int i = 0 ; i < dtolist.size() ; i++){
            String id = dtolist.get(i).getId();
            String title = dtolist.get(i).getTitle();
            String date =dtolist.get(i).getDate();
            Log.d("게시판글","아이디:"+id+",제목:"+title+",시간:"+date);
            adapter.addItems(new SangWaDTO(id,title,date));
        }
    }
}

