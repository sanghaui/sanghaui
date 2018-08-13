package com.example.home.sample;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Camera_activity extends AppCompatActivity {
    public static String IMAGE_FILE = "capture.jpg";
    Button captureBtn, closeBtn, saveBtn, cancelBtn;
    ImageView imgpreview,imageView;
    FrameLayout previewFrame;
    String folder = "cordi";
    String top = "top";
    String bottom = "bottom";
    String main = "main";
    String mix = "mix";

    String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath();
//    String foler_name = "/"+folder+"/";
    String main_name = "/"+folder+"/"+main+"/";
    String top_name = "/"+folder+"/"+top+"/";
    String bottom_name = "/"+folder+"/"+bottom+"/";
    String mix_name = "/"+folder+"/"+mix+"/";
//    String string_path1 = ex_storage+foler_name;
    String string_path2 = ex_storage+main_name;
    String string_path3 = ex_storage+top_name;
    String string_path4 = ex_storage+bottom_name;
//    String string_path5 = ex_storage+mix_name;
/*    File file_path1 = new File(string_path1);
    File file_path2 = new File(string_path2);
    File file_path3 = new File(string_path3);
    File file_path4 = new File(string_path4);
    File file_path5 = new File(string_path5);*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_fragment);

        saveBtn = findViewById(R.id.saveBtn);
        closeBtn = findViewById(R.id.closeBtn);
        imageView = findViewById(R.id.imageView);
        imgpreview = findViewById(R.id.imgpreview);
        captureBtn = findViewById(R.id.captureBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        saveBtn.setVisibility(View.INVISIBLE);
        cancelBtn.setVisibility(View.INVISIBLE);
        imgpreview.setVisibility(View.INVISIBLE);

      /*  if(!file_path1.isDirectory()){
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
        }*/

        imageView.setImageResource(R.drawable.saram5);

        final CameraSurfaceView cameraView = new CameraSurfaceView(getApplicationContext());
        // vw = new SurfaceView(this);
        previewFrame = findViewById(R.id.previewFrame);
        previewFrame.addView(cameraView);

        // 버튼 이벤트 처리

        captureBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cameraView.capture(new Camera.PictureCallback() {
                    public void onPictureTaken(byte[] data, final Camera camera) {
                        try {

                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 4;
                            Canvas canvas = new Canvas();

                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);


                            Matrix m = new Matrix();
                            // matrix객체에 회전될 정보 입력
                            m.setRotate(90, (float) bitmap.getWidth(), (float) bitmap.getHeight());
                            // 기존에 저장했던 bmp를 Matrix를 적용하여 다시 생성
                            Bitmap rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);
                            // 기존에 생성했던 bmp 자원해제
                            bitmap.recycle();

                            Bitmap mask = BitmapFactory.decodeResource(getResources(), R.drawable.saram7);
                            final Bitmap result = Bitmap.createBitmap(rotateBitmap.getWidth(),rotateBitmap.getHeight(),Bitmap.Config.ARGB_8888);

                            mask = getResizedBitmap(mask, rotateBitmap.getHeight(), rotateBitmap.getWidth());
                            canvas.setBitmap(result);
                            canvas.drawBitmap(rotateBitmap, 0, 0 , null);
                            Paint paint = new Paint();
                            paint.setFilterBitmap(false);
                            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

                            canvas.drawBitmap(mask, 0, 0, paint);

                            camera.stopPreview();
                            imageView.setVisibility(View.INVISIBLE);
                            saveBtn.setVisibility(View.VISIBLE);
                            cancelBtn.setVisibility(View.VISIBLE);
                            imgpreview.setVisibility(View.VISIBLE);
                            Bitmap preImg = Bitmap.createBitmap(result);
                            imgpreview.setImageBitmap(preImg);
                            saveBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    for(int i = 0 ; i < 3 ; i++) {
                                        if(i == 0){
                                            long k = System.currentTimeMillis();
                                            FileOutputStream out = null;
                                            try {
                                                out = new FileOutputStream(string_path2 + k + "main.jpg");
                                            } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                            }
                                            result.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                        }else if(i == 1) {
                                            Bitmap topImg = Bitmap.createBitmap(result, 0, 0, 756, 504);
                                            long k = System.currentTimeMillis();
                                            FileOutputStream out = null;
                                            try {
                                                out = new FileOutputStream(string_path3 + k + "top.jpg");
                                            } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                            }
                                            topImg.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                        }else{
                                            Bitmap bottomImg = Bitmap.createBitmap(result, 0, 504, 756, 504);
                                            long k = System.currentTimeMillis();
                                            FileOutputStream out = null;
                                            try {
                                                out = new FileOutputStream(string_path4 + k + "bottom.jpg");
                                            } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                            }
                                            bottomImg.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                        }//if else if

                                    }//for
                                    Toast.makeText(getApplicationContext(), "카메라로 찍은 사진을 앨범에 저장했습니다.", Toast.LENGTH_LONG).show();
                                    saveBtn.setVisibility(View.INVISIBLE);
                                    cancelBtn.setVisibility(View.INVISIBLE);
                                    imgpreview.setImageResource(0);
                                    imageView.setVisibility(View.VISIBLE);
                                    camera.startPreview();
                                }//saveBtn - onClick
                            });//saveBtn - ClickListner

                            cancelBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(), "취소하였습니다.", Toast.LENGTH_LONG).show();
                                    cancelBtn.setVisibility(View.INVISIBLE);
                                    saveBtn.setVisibility(View.INVISIBLE);
                                    imgpreview.setImageResource(0);
                                    imageView.setVisibility(View.VISIBLE);
                                    camera.startPreview();
                                }
                            });//cancelBtn - ClickListner

                        } catch (Exception e) {
                            Log.e("SampleCapture", "Failed to insert image.", e);
                        }
                    }
                });



            }
        });

        checkDangerousPermissions();

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight,
                                          int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;
    }

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    /*
     * 카메라 미리보기를 위한 서피스뷰 정의
     */
    private class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;
        private Camera camera = null;

        public CameraSurfaceView(Context context) {
            super(context);

            mHolder = getHolder();
            mHolder.addCallback(this);

        }

        public void surfaceCreated(SurfaceHolder holder) {

            camera = Camera.open();


            try {
                camera.setPreviewDisplay(mHolder);
            } catch (Exception e) {
                Log.e("CameraSurfaceView", "Failed to set camera preview.", e);
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            int degrees = 0;

            switch (rotation) {

                case Surface.ROTATION_0: degrees = 0; break;

                case Surface.ROTATION_90: degrees = 90; break;

                case Surface.ROTATION_180: degrees = 180; break;

                case Surface.ROTATION_270: degrees = 270; break;

            }

            int result  = (90 - degrees + 360) % 360;

            camera.setDisplayOrientation(result);

            camera.startPreview();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }

        public boolean capture(Camera.PictureCallback handler) {
            if (camera != null) {
                camera.takePicture(null, null, handler);
                return true;
            } else {
                return false;
            }
        }

    }
}


