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
import android.widget.ListView;

import com.example.user.sangwa_test.Board.Adapters.NoticeAdapter;
import com.example.user.sangwa_test.Board.DTO.NoticeDTO;
import com.example.user.sangwa_test.R;

public class NoticeFragment extends Fragment {
    NoticeAdapter adapter;
    ListView noticelist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup notice = (ViewGroup) inflater.inflate(R.layout.notice_fragment,container,false);

        adapter =  new NoticeAdapter();

        adapter.addItems(new NoticeDTO("제목입니다1","내용입니다","아이디"));
        adapter.addItems(new NoticeDTO("제목입니다2","내용입니다","아이디"));
        adapter.addItems(new NoticeDTO("제목입니다3","내용입니다","아이디"));
        adapter.addItems(new NoticeDTO("제목입니다4","내용입니다","아이디"));

        noticelist = notice.findViewById(R.id.noticelist);
        noticelist.setAdapter(adapter);

        noticelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoticeDTO dto = (NoticeDTO) adapter.getItem(position);
                Intent noticeTouch = new Intent(getContext(), NoticeTouchActivity.class);
                noticeTouch.putExtra("title",dto.getTitle());
                startActivity(noticeTouch);
            }
        });

        return notice;
    }

}
