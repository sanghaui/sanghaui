package com.example.home.sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.home.sample.R;

import java.io.File;

public class MixAdapter extends PagerAdapter {
    LayoutInflater inflater;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/cordi/mix/";
    File file = new File(path);
    private int imgCount;

    public MixAdapter(LayoutInflater inflater) {


        // TODO Auto-generated constructor stub



        //전달 받은 LayoutInflater를 멤버변수로 전달

        this.inflater=inflater;


    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        imgCount = file.listFiles().length;

        return imgCount * 3; //이미지 개수 리턴(그림이 10개라서 10을 리턴)
    }

    @Override

    public void destroyItem(ViewGroup container2, int position, Object object) {

        // TODO Auto-generated method stub



        //ViewPager에서 보이지 않는 View는 제거

        //세번째 파라미터가 View 객체 이지만 데이터 타입이 Object여서 형변환 실시

        container2.removeView((View)object);



    }

    @Override

    public Object instantiateItem(ViewGroup container2, int position) {

        // TODO Auto-generated method stub




        View view=null;
        //View view2=null;



        //새로운 View 객체를 Layoutinflater를 이용해서 생성

        //만들어질 View의 설계는 res폴더>>layout폴더>>viewpater_childview.xml 레이아웃 파일 사용

        view = inflater.inflate(R.layout.viewpager_mix, null);
        //view2= inflater.inflate(R.layout.viewpager_childview2, null);



        //만들어진 View안에 있는 ImageView 객체 참조

        //위에서 inflated 되어 만들어진 view로부터 findViewById()를 해야 하는 것에 주의.

        ImageView img= (ImageView)view.findViewById(R.id.img_viewpager_mix);
        //ImageView img2= (ImageView)view2.findViewById(R.id.img2_viewpager_childimage2);



        //ImageView에 현재 position 번째에 해당하는 이미지를 보여주기 위한 작업

        //현재 position에 해당하는 이미지를 setting


        //Bitmap src = BitmapFactory.decodeFile( Environment.getExternalStorageDirectory().getAbsolutePath()+"/cordi/top/.jpg");
        //1532051702911top.jpg

        String str;
        int num = 0;
        int imgCount = file.listFiles().length;
        position %= imgCount;
        Bitmap[] map = new Bitmap[imgCount];
        if(file.listFiles().length > 0 ){
            for(File f : file.listFiles()){
                str = f.getName();
                map[num] = BitmapFactory.decodeFile(path+"/"+str);
                num++;
            }
        }

        img.setImageBitmap(map[position]);

        //ViewPager에 만들어 낸 View 추가
        container2.addView(view);
        //container.addView(view2);





        //Image가 세팅된 View를 리턴

        return view;

    }





    //instantiateItem() 메소드에서 리턴된 Ojbect가 View가  맞는지 확인하는 메소드

    @Override

    public boolean isViewFromObject(View v, Object obj) {

        // TODO Auto-generated method stub

        return v==obj;

    }
}