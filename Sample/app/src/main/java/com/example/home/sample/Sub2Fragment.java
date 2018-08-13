package com.example.home.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Sub2Fragment extends Fragment {
    MainActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup sub2 = (ViewGroup) inflater.inflate(R.layout.sub2_fragment, container,false);
        //게시판 버튼
        Button board = sub2.findViewById(R.id.boardbutton);
        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.buttonChange2(0);

            }
        });
        //공지사항 버튼
        Button notice = sub2.findViewById(R.id.noticebutton);
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.buttonChange2(1);
            }
        });

        return sub2;
    }
}
