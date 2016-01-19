package com.example.wojtek.imagefromcameratodb;

import android.app.Activity;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;

/**
 * Created by Wojtek on 2016-01-19.
 */
public class Adapterek extends Activity implements ListAdapter {
    DBSingleton dbSingleton = DBSingleton.getInstance(this);
    final SQLiteDatabase db = dbSingleton.getWritableDatabase();

    private LayoutInflater inflater;
    private Bitmap bmp;

    public Adapterek (LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    View wiersz;
        if (convertView != null){
            wiersz = convertView;
        }
        else{
            wiersz = inflater.inflate(R.layout.row, null);
        }
        ImageView imageView = (ImageView) wiersz.findViewById(R.id.imageview);

        Cursor c = db.rawQuery("select * from tb",null);
        Log.i("test",String.valueOf(c.getCount()));
        if(c.moveToPosition(position)){
            byte[] image = c.getBlob(0);
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            imageView.setImageBitmap(bmp);
        }
     return wiersz;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
