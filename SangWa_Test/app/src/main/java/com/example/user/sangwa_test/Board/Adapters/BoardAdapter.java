package com.example.user.sangwa_test.Board.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.sangwa_test.Board.DTO.SangWaDTO;
import com.example.user.sangwa_test.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class BoardAdapter extends BaseAdapter {
    ArrayList<SangWaDTO> boardItems = new ArrayList<>();
    //이미지 로더 생성
    ImageLoader imageLoader;
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
        //이미지 로딩 메서드
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.blank) // 기본이미지
                .showImageForEmptyUri(R.drawable.blank) // 주소가 없을 경우
                .showImageOnFail(R.drawable.blank)// 실패했을 경우
                .build();

        ImageLoaderConfiguration config =
                new ImageLoaderConfiguration.Builder(context)
                        .defaultDisplayImageOptions(options)
                        .build();
        imageLoader.getInstance().init(config);
        ///

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.board_item, parent, false);

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleText = (TextView) convertView.findViewById(R.id.titleText);
        TextView idText = (TextView) convertView.findViewById(R.id.idText);
        TextView dateText = (TextView) convertView.findViewById(R.id.dateText);
        ImageView imageView = convertView.findViewById(R.id.imageView1);
        final ProgressBar progressBar = convertView.findViewById(R.id.progressBar);

        SangWaDTO item = boardItems.get(position);
        Log.d("어뎁터", "제목:" + item.getTitle() + "작성일" + item.getDate()+"이미지주소"+item.getImgRes());
        titleText.setText(item.getTitle());
        idText.setText(item.getId());
        dateText.setText(item.getDate());
        ImageLoader.getInstance().displayImage(item.getImgRes(),imageView, new ImageLoadingListener() {
            //이미지를 불러올 인터넷 상의 이미지 경로,붙여줄 이미지뷰,옵션
            @Override
            public void onLoadingStarted(String s, View view) {
                //이미지 로딩을 시작할때
                progressBar.setVisibility(view.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                //이미지 로딩을 실패 했을때
                progressBar.setVisibility(view.GONE);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                //이미지 로딩 완료 했을때
                progressBar.setVisibility(view.GONE);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                //이미지 로딩 취소 됬을때
                progressBar.setVisibility(view.GONE);
            }
        });
        return convertView;
    }

}
