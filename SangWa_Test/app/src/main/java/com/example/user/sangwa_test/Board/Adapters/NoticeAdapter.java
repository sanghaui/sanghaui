package com.example.user.sangwa_test.Board.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.sangwa_test.Board.DTO.SangWaDTO;
import com.example.user.sangwa_test.R;

import java.util.ArrayList;

public class NoticeAdapter extends BaseAdapter {
    ArrayList<SangWaDTO> NoticeItems = new ArrayList<>();
    //빈생성자
    public NoticeAdapter() {}


    public void addItems(SangWaDTO dto) {
        NoticeItems.add(dto);
    }

    @Override
    public int getCount() {
        return NoticeItems.size();
    }

    @Override
    public Object getItem(int position) {
        return NoticeItems.get(position);
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
        convertView = inflater.inflate(R.layout.notice_item,parent,false);

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleText = (TextView) convertView.findViewById(R.id.noticeTitleText) ;
        TextView idText = (TextView) convertView.findViewById(R.id.noticeIdText) ;
        TextView contentText= (TextView) convertView.findViewById(R.id.noticeContentText) ;

        SangWaDTO item = NoticeItems.get(position);
        titleText.setText(item.getTitle());
        idText.setText(item.getId());
        contentText.setText(item.getContent());


        return convertView;
    }
}
