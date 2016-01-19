package com.example.wojtek.imagefromcameratodb;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private String currentPhotoPath;
    public static SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button takepic = (Button)findViewById(R.id.takePic);
        Button sendPic = (Button)findViewById(R.id.sendPic);
        Button gallery = (Button)findViewById(R.id.gallery);
        DBSingleton dbSingleton = DBSingleton.getInstance(this);
        final SQLiteDatabase db = dbSingleton.getWritableDatabase();
//        db = this.openOrCreateDatabase("test.db", Context.MODE_PRIVATE,null);
//        db.execSQL("create table if not exists tb (a blob)");
        takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageFileName = "JPEG_" + timeStamp + "_";
                    File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    try {
                        File photoFile = File.createTempFile(imageFileName, ".jpg", storageDir);
                        currentPhotoPath = photoFile.getAbsolutePath();
                        Log.i("tesT", String.valueOf(currentPhotoPath));
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        startActivityForResult(takePictureIntent, 708);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        sendPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPhotoPath != null) {

                    try {
                        FileInputStream fis = new FileInputStream(currentPhotoPath);
                        byte[] image = new byte[fis.available()];
                        fis.read(image);
                        ContentValues values = new ContentValues();
                        values.put("a", image);
                        db.insert("tb", null, values);
                        fis.close();
                        Toast.makeText(getApplicationContext(),"Insert dziala",Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"nie wczytales zdjecia",Toast.LENGTH_SHORT).show();
                }
            }

        });
        gallery.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
            Intent i = new Intent(MainActivity.this,GaleryActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView mImageView = (ImageView)findViewById(R.id.imageview);
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photow = bmOptions.outWidth;
        int photoh = bmOptions.outHeight;
        int scaleFactor = Math.min(photow / targetW, photoh / targetH);
        bmOptions.inJustDecodeBounds= false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

}
