package com.example.user.sangwa_test.Board;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.user.sangwa_test.Board.Adapters.BoardAdapter;
import com.example.user.sangwa_test.Board.DTO.SangWaDTO;
import com.example.user.sangwa_test.R;

public class BoardFragment extends Fragment {

    Button writeButton;
    BoardAdapter adapter;
    ListView boardList;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup board = (ViewGroup) inflater.inflate(R.layout.board_fragment, container, false);

        //임의값 설정
        adapter = new BoardAdapter();
        adapter.addItems(new SangWaDTO("제목1","아이디1","작성일1"));
        adapter.addItems(new SangWaDTO("제목2","아이디2","작성일2"));
        adapter.addItems(new SangWaDTO("제목3","아이디3","작성일3"));
        adapter.addItems(new SangWaDTO("제목4","아이디4","작성일4"));

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
}
