package com.example.wojtek.imagefromcameratodb;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Wojtek on 2016-01-19.
 */
public class GaleryActivity extends Activity {
    private ArrayAdapter<ImageView> listAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_gallery);
//        ImageView imageView = (ImageView) findViewById(R.id.imageView2);

        ListView lv = new ListView(this);
        lv.setAdapter(new Adapterek(getLayoutInflater()));

        setContentView(lv);
//        ArrayList<ImageView> obrazki = new ArrayList<ImageView>(R.id.listView);
//        listAdapter = new ArrayAdapter<ImageView>(this,R.id.imageview,obrazki);
//        Cursor c = db.rawQuery("select * from tb",null);
//        if(c.moveToNext()){
//            byte[] image = c.getBlob(0);
//            Bitmap bmp = BitmapFactory.decodeByteArray(image,0,image.length);
//            imageView.setImageBitmap(bmp);
//        }

    }

}
