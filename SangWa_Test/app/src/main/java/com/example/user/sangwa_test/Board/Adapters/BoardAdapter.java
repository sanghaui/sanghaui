package com.example.user.sangwa_test.Board.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.sangwa_test.Board.DTO.SangWaDTO;
import com.example.user.sangwa_test.R;

import java.util.ArrayList;

public class BoardAdapter extends BaseAdapter {
    ArrayList<SangWaDTO> boardItems = new ArrayList<>();

           //빈생성자
    public BoardAdapter() {

    }
    //값 추가
    public void addItems(SangWaDTO dto) {
        boardItems.add(dto);
    }

    //길이
    @Override
    public int getCount() {
        return boardItems.size();
    }
    //아이템 위치
    @Override
    public Object getItem(int position) {
        return boardItems.get(position);
    }
    //
    @Override
    public long getItemId(int position) {
        return position;
    }
    //뷰설정
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //부모 context 가져오기
        final Context context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.board_item,parent,false);

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleText = (TextView) convertView.findViewById(R.id.titleText) ;
        TextView idText = (TextView) convertView.findViewById(R.id.idText) ;
        TextView dateText= (TextView) convertView.findViewById(R.id.dateText) ;

        SangWaDTO item = boardItems.get(position);
        Log.d("어뎁터","제목:"+item.getTitle()+"작성일"+item.getDate());
        titleText.setText(item.getTitle());
        idText.setText(item.getId());
        dateText.setText(item.getDate());

        return convertView;
    }
}
