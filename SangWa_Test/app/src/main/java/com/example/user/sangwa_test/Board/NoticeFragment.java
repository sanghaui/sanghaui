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
import android.widget.ListView;

import com.example.user.sangwa_test.Board.Adapters.NoticeAdapter;
import com.example.user.sangwa_test.Board.DTO.SangWaDTO;
import com.example.user.sangwa_test.DBconnectionNoticereader;
import com.example.user.sangwa_test.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NoticeFragment extends Fragment {
    NoticeAdapter adapter;
    ListView noticelist;

    ArrayList<SangWaDTO> dtolist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup notice = (ViewGroup) inflater.inflate(R.layout.notice_fragment,container,false);

        adapter =  new NoticeAdapter();
        getList();

        noticelist = notice.findViewById(R.id.noticelist);
        noticelist.setAdapter(adapter);

        noticelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SangWaDTO dto = (SangWaDTO) adapter.getItem(position);
                Intent noticeTouch = new Intent(getContext(), NoticeTouchActivity.class);
                noticeTouch.putExtra("title",dto.getTitle());
                noticeTouch.putExtra("date",dto.getDate());
                noticeTouch.putExtra("id",dto.getId());
                noticeTouch.putExtra("content",dto.getContent());
                startActivity(noticeTouch);
            }
        });

        return notice;
    }

    @Override
    public void onResume() {
        adapter =  new NoticeAdapter();
        getList();

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
        adapter = new NoticeAdapter();

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
