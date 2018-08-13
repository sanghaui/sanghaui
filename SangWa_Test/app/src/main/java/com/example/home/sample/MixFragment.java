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

import java.io.File;

public class MixFragment extends Fragment {
    //MainActivity activity;
    ViewPager pager_mix;

    String pathMix = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cordi/mix/";
    File fileMix = new File(pathMix);
    private final int CountMix = fileMix.listFiles().length;


/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container2, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup mixfrag = (ViewGroup) inflater.inflate(R.layout.mix_fragment,container2,false);

        pager_mix = mixfrag.findViewById(R.id.pager_mix);

        MixAdapter adapterMix= new MixAdapter(getLayoutInflater());
        pager_mix.setAdapter(adapterMix);
        pager_mix.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position < CountMix){
                    pager_mix.setCurrentItem(position + CountMix, false);
                } else if (position > CountMix * 2){
                    pager_mix.setCurrentItem(position - CountMix, false);
                }

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return mixfrag;
    }


}
