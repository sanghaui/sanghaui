package com.example.user.sangwa_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.user.sangwa_test.Board.BoardFragment;
import com.example.user.sangwa_test.Board.BottomFragment;
import com.example.user.sangwa_test.Board.NoticeFragment;

public class MainActivity extends AppCompatActivity {
    Button closet;
    Button commu;
    public static String userID;
    Sub1Fragment sub1Fragment;
    Sub2Fragment sub2Fragment;
    BoardFragment boardFragment;
    NoticeFragment noticeFragment;
    TopFragment topFragment;
    BottomFragment bottomFragment;
    AllFragment allFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userID="테스터";
        closet = findViewById(R.id.closet);
        commu = findViewById(R.id.commu);


        sub1Fragment = new Sub1Fragment();
        sub2Fragment = new Sub2Fragment();

        topFragment = new TopFragment();
        bottomFragment = new BottomFragment();
        allFragment = new AllFragment();


        boardFragment = new BoardFragment();
        noticeFragment = new NoticeFragment();

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


    }


    public void buttonChange1(int index){

        //게시판 && 공지사항
        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.container2,topFragment).commit();
        }else if(index == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.container2,bottomFragment).commit();
        }else if(index ==2 ){
            getSupportFragmentManager().beginTransaction().replace(R.id.container2,allFragment).commit();
        }

    }

    public void buttonChange2(int index){

        //게시판 && 공지사항
        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.container2,boardFragment).commit();
        }else if(index == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.container2,noticeFragment).commit();
        }

    }

}
