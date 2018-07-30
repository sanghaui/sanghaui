package com.example.user.sangwa_test;

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
import android.widget.ProgressBar;

import com.example.user.sangwa_test.Board.DTO.SangWaDTO;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class Update_Activity extends AppCompatActivity {

    String id, title, content, imgRes;
    int index;

    EditText editTitle;
    EditText editid;
    EditText editContent;
    EditText editpw;
    Button submit;
    Button cancel;
    Button imageLoad;
    ImageView ImageRes;
    ProgressBar progressBar;

    //이미지로더
    com.nostra13.universalimageloader.core.ImageLoader imageLoader;

    public String imageFilePathA, uploadPathA, uploadFileName, encodedImage;
    final int LOAD_IMAGE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_writer);

        editTitle = findViewById(R.id.editTitle);
        editid = findViewById(R.id.editid);
        editContent = findViewById(R.id.editContent);
        editpw = findViewById(R.id.editpw);
        ImageRes = findViewById(R.id.ImageRes);
        progressBar = findViewById(R.id.progressBar);

        //이미지 로딩 메서드
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.blank) // 기본이미지
                .showImageForEmptyUri(R.drawable.blank) // 주소가 없을 경우
                .showImageOnFail(R.drawable.blank)// 실패했을 경우
                .build();

        ImageLoaderConfiguration config =
                new ImageLoaderConfiguration.Builder(getApplicationContext())
                        .defaultDisplayImageOptions(options)
                        .build();
        imageLoader.getInstance().init(config);
        ///

        //가져온값 세팅
        Intent intent = getIntent();
        insert(intent);
        Log.d("아이디", id);

        //뷰어에 셋팅
        editTitle.setText(title);
        editid.setText(id);
        editContent.setText(content);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지 만들기
                try {
                    if (imageFilePathA != null && imageFilePathA != "") {
                        Bitmap bitmap = ImageLoader.init().from(imageFilePathA).getBitmap();
                        encodedImage = ImageBase64.encode(bitmap);  //String 형태로 변환
                        Log.d("Sub1Add:encodedImg=>: ", encodedImage);
                    }
                } catch (Exception e) {
                    Log.d("이미지", "이미지 생성 오류");
                    e.printStackTrace();
                }

                SangWaDTO dto = new SangWaDTO();
                dto.setTitle(editTitle.getText().toString());
                dto.setId(editid.getText().toString());
                dto.setContent(editContent.getText().toString());
                dto.setPw(editpw.getText().toString());
                dto.setImgRes(uploadFileName);
                dto.setEncodedImage(encodedImage);
                DBconnectionUpdater db = new DBconnectionUpdater();
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
                intent.setDataAndType(uri, "image/*");
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


        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(imgRes, ImageRes, new ImageLoadingListener() {
            //이미지를 불러올 인터넷 상의 이미지 경로,붙여줄 이미지뷰,옵션
            @Override
            public void onLoadingStarted(String s, View view) {
                //이미지 로딩을 시작할때
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                //이미지 로딩을 실패 했을때
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                //이미지 로딩 완료 했을때
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                //이미지 로딩 취소 됬을때
            }
        });
        return;

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

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
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

    //가져온 값 할당
    public void insert(Intent intent) {
        index = intent.getIntExtra("index", index);
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        imgRes = intent.getStringExtra("imgRes");
    }
}