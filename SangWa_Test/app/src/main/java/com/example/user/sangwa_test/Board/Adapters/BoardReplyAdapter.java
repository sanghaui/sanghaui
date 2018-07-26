package com.example.user.sangwa_test.Board.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.sangwa_test.Board.DTO.BoardReplyDTO;
import com.example.user.sangwa_test.R;

import java.util.ArrayList;

public class BoardReplyAdapter extends BaseAdapter {
    ArrayList<BoardReplyDTO> BoardItems = new ArrayList<BoardReplyDTO>();
        //빈생성자
    public BoardReplyAdapter() {}


    public void addItems(BoardReplyDTO dto) {
        BoardItems.add(dto);
    }

    @Override
    public int getCount() {
        return BoardItems.size();
    }

    @Override
    public Object getItem(int position) {
        return BoardItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //부모 context 가져오기
        final Context context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.board_reply_item,parent,false);

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleText = (TextView) convertView.findViewById(R.id.noticeTitleText) ;
        TextView idText = (TextView) convertView.findViewById(R.id.noticeIdText) ;
        TextView contentText= (TextView) convertView.findViewById(R.id.noticeContentText) ;

        BoardReplyDTO item = BoardItems.get(position);
        titleText.setText(item.getTitle());
        idText.setText(item.getId());
        contentText.setText(item.getContent());

        return convertView;
    }
}
