package com.example.user.sangwa_test.Board;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.sangwa_test.Board.DTO.SangWaDTO;
import com.example.user.sangwa_test.DBconnectionWriter;
import com.example.user.sangwa_test.R;


public class BoardWriter extends AppCompatActivity {
    EditText editTitle;
    EditText editid;
    EditText editContent;
    EditText editpw;
    Button submit;
    Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_writer);

        editTitle = findViewById(R.id.editTitle);
        editid = findViewById(R.id.editid);
        editContent = findViewById(R.id.editContent);
        editpw= findViewById(R.id.editpw);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SangWaDTO dto =new SangWaDTO();
                dto.setTitle(editTitle.getText().toString());
                dto.setId(editid.getText().toString());
                dto.setContent(editContent.getText().toString());
                dto.setPw(editpw.getText().toString());
                DBconnectionWriter db = new DBconnectionWriter();
                db.insert(dto);
                db.execute();
                finish();
            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
