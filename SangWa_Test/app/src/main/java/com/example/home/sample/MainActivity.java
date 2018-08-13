package com.example.home.sample;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    Button closet;
    Button commu;
    Button mainfrag;
    ActionBar abar;
    int Q = 0;
    Sub1Fragment sub1Fragment;
    Sub2Fragment sub2Fragment;
    Mainfragment mainfragment;
    MixFragment mixFragment;
    AllFragment allFragment;
    NoticeFragment noticeFragment;
    BoardFragment boardFragment;
    CoordiFragment coordiFragment;
    Sub3Fragment sub3Fragment;
    String login = "";

    String folder = "cordi";
    String top = "top";
    String bottom = "bottom";
    String main = "main";
    String mix = "mix";

    String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath();
    String foler_name = "/"+folder+"/";
    String main_name = "/"+folder+"/"+main+"/";
    String top_name = "/"+folder+"/"+top+"/";
    String bottom_name = "/"+folder+"/"+bottom+"/";
    String mix_name = "/"+folder+"/"+mix+"/";
    String string_path1 = ex_storage+foler_name;
    String string_path2 = ex_storage+main_name;
    String string_path3 = ex_storage+top_name;
    String string_path4 = ex_storage+bottom_name;
    String string_path5 = ex_storage+mix_name;
    File file_path1 = new File(string_path1);
    File file_path2 = new File(string_path2);
    File file_path3 = new File(string_path3);
    File file_path4 = new File(string_path4);
    File file_path5 = new File(string_path5);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        if(!file_path1.isDirectory()){
            file_path1.mkdirs();
        }
        if(!file_path2.isDirectory()){
            file_path2.mkdirs();
        }
        if(!file_path3.isDirectory()){
            file_path3.mkdirs();
        }
        if(!file_path4.isDirectory()){
            file_path4.mkdirs();
        }
        if(!file_path5.isDirectory()){
            file_path5.mkdirs();
        }

        setTitle("상하의 App");
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);
        closet = findViewById(R.id.closet);
        commu = findViewById(R.id.commu);
        mainfrag = findViewById(R.id.mainfrag);

        sub1Fragment = new Sub1Fragment();
        sub2Fragment = new Sub2Fragment();
        mainfragment = new Mainfragment();
        mixFragment = new MixFragment();
        allFragment = new AllFragment();
        noticeFragment = new NoticeFragment();
        boardFragment = new BoardFragment();
        coordiFragment = new CoordiFragment();
        sub3Fragment = new Sub3Fragment();


        getSupportFragmentManager().beginTransaction().replace(R.id.container2,mainfragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,sub3Fragment).commit();

        closet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container,sub1Fragment).commit();
            }
        });



        commu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container,sub2Fragment).commit();

            }
        });
        mainfrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container2,mainfragment).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,sub3Fragment).commit();


                setTitle("상하의 App");
            }
        });


    }

    //메뉴바 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //service에서 로그인값 받아오기
        /*login=(String)getSystemService(login);*/
        if (login.equals("")){
            getMenuInflater().inflate(R.menu.menu_main, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_afterlogin, menu);
        }



        return true;
    }

    public void buttonChange1(int index){

        //게시판 && 공지사항
        if(index == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.container2,mixFragment).commit();
            setTitle("완성코디");
        }else if(index ==2 ){
            getSupportFragmentManager().beginTransaction().replace(R.id.container2,allFragment).commit();
            setTitle("원본보기");
        }else if(index ==3 ){
            getSupportFragmentManager().beginTransaction().replace(R.id.container2,coordiFragment).commit();
            setTitle("코디화면");
        }

    }



    public void buttonChange2(int index){

        //게시판 && 공지사항
        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.container2,boardFragment).commit();
            setTitle("게시판");
        }else if(index == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.container2,noticeFragment).commit();
            setTitle("공지사항");
        }

    }

    //메뉴셀렉트값 받아오기
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId){
            case R.id.menu_login :
                Q = 1;
                Toast.makeText(this, "로그인", Toast.LENGTH_SHORT).show();
                pageMove(Q);
                break;

            case R.id.menu_register :
                Q = 2;
                Toast.makeText(this, "회원가입", Toast.LENGTH_SHORT).show();
                pageMove(Q);
                break;

            case R.id.menu_logout :
                Q = 3;
                pageMove(Q);
                Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    //Q값을 변수로 받아 페이지 이동
    private void pageMove(int Q) {

        switch (Q){
            case 1 :

                break;

            case 2 :

                break;

            case 3 :

                break;
        }
    }
}
