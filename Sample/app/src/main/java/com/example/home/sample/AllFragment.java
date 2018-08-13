package com.example.home.sample;


import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class AllFragment extends Fragment {
    ImageView imageView1;
//    MainActivity activity;

    ViewPager pager_all;

    String pathMain = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cordi/main/";
    File fileMain = new File(pathMain);
    private final int CountMain = fileMain.listFiles().length;

/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container2, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup allfrag = (ViewGroup) inflater.inflate(R.layout.allfragment,container2,false);

        pager_all = allfrag.findViewById(R.id.pager_all);
        AllAdapter adapterMain= new AllAdapter(getLayoutInflater());
        pager_all.setAdapter(adapterMain);
        pager_all.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position < CountMain){
                    pager_all.setCurrentItem(position + CountMain, false);
                } else if (position > CountMain * 2){
                    pager_all.setCurrentItem(position - CountMain, false);
                }

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return allfrag;
    }


}
