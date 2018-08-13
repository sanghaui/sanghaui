package com.example.home.sample.Board;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.home.sample.DBconnectionWriter;
import com.example.home.sample.R;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;


public class BoardWriter extends AppCompatActivity {
    EditText editTitle;
    EditText editid;
    EditText editContent;
    EditText editpw;
    Button submit;
    Button cancel;
    Button imageLoad;
    ImageView ImageRes;

    public String imageFilePathA, uploadPathA, uploadFileName, encodedImage;
    final int LOAD_IMAGE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_writer);

        editTitle = findViewById(R.id.editTitle);
        editid = findViewById(R.id.editid);
        editContent = findViewById(R.id.editContent);
        editpw= findViewById(R.id.editpw);
        ImageRes = findViewById(R.id.ImageRes);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지 만들기
                try {
                if(imageFilePathA != null && imageFilePathA != "") {
                    Bitmap bitmap = ImageLoader.init().from(imageFilePathA).getBitmap();
                    encodedImage = ImageBase64.encode(bitmap);  //String 형태로 변환
                    Log.d("Sub1Add:encodedImg=>: ", encodedImage);
                }
                }catch(Exception e){
                    Log.d("이미지","이미지 생성 오류");
                    e.printStackTrace();
                }

                SangWaDTO dto =new SangWaDTO();
                dto.setTitle(editTitle.getText().toString());
                dto.setId(editid.getText().toString());
                dto.setContent(editContent.getText().toString());
                dto.setPw(editpw.getText().toString());
                dto.setImgRes(uploadFileName);
                dto.setEncodedImage(encodedImage);
                DBconnectionWriter db = new DBconnectionWriter();
                db.insert(dto);
                db.execute();
                finish();
            }
        });

        imageLoad = findViewById(R.id.imageLoad);
        imageLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/cordi/mix/");
                Intent intent = new Intent();
                intent.setDataAndType(uri,"image/*");
                /*intent.setType("image/*");*/
                intent.setAction(intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            try {
                String path = "";
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    path = getPathFromURI(selectedImageUri);
                    //Log.d("Main", "Image Path : " + path);
                }

                imageFilePathA = path;
                uploadFileName = imageFilePathA.split("/")[imageFilePathA.split("/").length - 1];

                //ImageView imageView = (ImageView) findViewById(R.id.imageView);
                ImageRes.setImageBitmap(BitmapFactory.decodeFile(path));

            } catch (Exception e){
                e.printStackTrace();
            }
        }else {
            Log.d("Main => ", "imagepath is null, whatever something is wrong!!");
        }
        /*super.onActivityResult(requestCode, resultCode, data);*/
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}
