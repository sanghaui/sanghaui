package com.example.user.sangwa_test.Board;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.user.sangwa_test.DBconnectionDeleter;
import com.example.user.sangwa_test.R;
import com.example.user.sangwa_test.Update_Activity;

public class ModiDel extends AppCompatActivity {
    Button modify, delete;
    DBconnectionDeleter deleter;
    String id, title, content, imgRes;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modi_del);
        Intent intent = getIntent();
        insert(intent);

        Log.d("수정삭제", String.valueOf(index));

        deleter = new DBconnectionDeleter();
        modify = findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModiDel.this);
                builder.setTitle("선택하신 글을 수정합니다");
                builder.setMessage("수정 하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("롱터치", "예버튼"+id);
                        finish();
                        Intent update = new Intent(getApplicationContext(), Update_Activity.class);
                        update.putExtra("index",index);
                        update.putExtra("id",id);
                        update.putExtra("title",title);
                        update.putExtra("content",content);
                        update.putExtra("imgRes",imgRes);
                        startActivity(update);
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("롱터치", "아니오버튼");
                    }
                });
                builder.show();
            }
        });
        //삭제처리
        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModiDel.this);
                builder.setTitle("선택하신 글을 삭제합니다");
                builder.setMessage("삭제 하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("롱터치", "예버튼");
                        deleter.insert(index);
                        deleter.execute();
                        finish();
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("롱터치", "아니오버튼");
                    }
                });
                builder.show();
            }
        });
    }

    public void insert(Intent intent) {
        index = intent.getIntExtra("index",index);
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        imgRes = intent.getStringExtra("imgRes");
    }
}
