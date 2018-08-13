package com.example.home.sample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class CoordiFragment extends Fragment {

//    MainActivity activity;

    ViewPager pager_top, pager_bottom;
    Button btn_save;

    String pathTop = Environment.getExternalStorageDirectory().getAbsolutePath()+"/cordi/top/";
    File fileTop = new File(pathTop);

    String pathBottom = Environment.getExternalStorageDirectory().getAbsolutePath()+"/cordi/bottom/";
    File fileBottom = new File(pathBottom);

    private final int CountTop = fileTop.listFiles().length;
    private final int CountBottom = fileBottom.listFiles().length;

/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container2, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup coordifrag = (ViewGroup) inflater.inflate(R.layout.coordifragment,container2,false);

        pager_top = coordifrag.findViewById(R.id.pager_top);
        pager_bottom = coordifrag.findViewById(R.id.pager_bottom);
        btn_save = coordifrag.findViewById(R.id.btn_save);

        CordiTopAdapter cordiTopAdapter = new CordiTopAdapter(getLayoutInflater());
        CordiBottomAdapter cordiBottomAdapter = new CordiBottomAdapter(getLayoutInflater());

        pager_top.setAdapter(cordiTopAdapter);
        pager_top.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position < CountTop){
                    pager_top.setCurrentItem(position + CountTop, false);
                } else if (position > CountTop * 2){
                    pager_top.setCurrentItem(position - CountTop, false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pager_bottom.setAdapter(cordiBottomAdapter);
        pager_bottom.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position < CountBottom){
                    pager_bottom.setCurrentItem(position + CountBottom, false);
                } else if (position > CountBottom * 2){
                    pager_bottom.setCurrentItem(position - CountBottom, false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pager_top.buildDrawingCache();
                pager_top.setDrawingCacheEnabled(true);
                Bitmap cacheTop = pager_top.getDrawingCache(true);
                pager_bottom.buildDrawingCache();
                pager_bottom.setDrawingCacheEnabled(true);
                Bitmap cacheBottom = pager_bottom.getDrawingCache(true);

                try {
                    combineImage(cacheTop, cacheBottom, true);
                    Toast.makeText(getActivity(), "저장 완료", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                onResume();
            }
        });


        return coordifrag;
    }

    public void combineImage(Bitmap first, Bitmap second, boolean isVerticalMode) throws FileNotFoundException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        options.inDither = true;
        options.inPurgeable = true;

        Bitmap hapsung = null;

        if(isVerticalMode){
            hapsung = Bitmap.createScaledBitmap(first, first.getWidth(), first.getHeight()+second.getHeight(), true);
        }else{
            hapsung = Bitmap.createScaledBitmap(first, first.getWidth()+second.getWidth(), first.getHeight(), true);
        }

        Paint p = new Paint();
        p.setDither(true);
        p.setFlags(Paint.ANTI_ALIAS_FLAG);

        Canvas c = new Canvas(hapsung);
        c.drawBitmap(first, 0, 0, p);
        if(isVerticalMode){
            c.drawBitmap(second, 0 , first.getHeight(), p);
        }else{
            c.drawBitmap(second, first.getWidth() , 0, p);
        }

        //first.recycle();
        //second.recycle();

        long k = System.currentTimeMillis();
        String string_path2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cordi/mix/";
        FileOutputStream out = new FileOutputStream(string_path2 + k + "mix.jpg");
        hapsung.compress(Bitmap.CompressFormat.JPEG, 100, out);

    }

}
