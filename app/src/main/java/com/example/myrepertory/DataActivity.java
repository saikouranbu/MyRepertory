package com.example.myrepertory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;


public class DataActivity extends Activity {
    private EditText name, artist, genre, data, dam, joy;
    private RatingBar singable, priority;
    private SQLiteDatabase db;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_data);DBOpenHelper dbHelper = new DBOpenHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 1);

        name = (EditText) findViewById(R.id.nameEdit3);
        name.setText(searchData(id, "name"));
        name.setKeyListener(null);

        artist = (EditText) this.findViewById(R.id.artistEdit3);
        artist.setText(searchData(id, "artist"));
        artist.setKeyListener(null);

        genre = (EditText) this.findViewById(R.id.genreEdit3);
        genre.setText(searchData(id, "genre"));
        genre.setKeyListener(null);

        data = (EditText) this.findViewById(R.id.musicEdit3);
        data.setText(searchData(id, "data"));
        data.setKeyListener(null);

        dam = (EditText) this.findViewById(R.id.damEdit3);
        dam.setText(searchData(id, "dam"));
        dam.setKeyListener(null);

        joy = (EditText) this.findViewById(R.id.joyEdit3);
        joy.setText(searchData(id, "joy"));
        joy.setKeyListener(null);

        singable = (RatingBar) findViewById(R.id.singableRate3);
        singable.setRating(searchRate(id, "singable"));
        singable.setIsIndicator(true);
        priority = (RatingBar) findViewById(R.id.priorityRate3);
        priority.setRating(searchRate(id, "priority"));
        priority.setIsIndicator(true);
    }

    public String searchData(int id, String column) {
        String musicData = "";
        String[] columns = new String[1];
        columns[0] = column;
        Cursor cursor = null;
        try {
            cursor = db.query("repertory", columns, "id = " + id, null, null, null, null);

            while (cursor.moveToNext()) {
                musicData = cursor.getString(cursor.getColumnIndex(column));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            return musicData;
        }
    }

    public float searchRate(int id, String column) {
        float musicRate = 0;
        String[] columns = new String[1];
        columns[0] = column;
        Cursor cursor = null;
        try {
            cursor = db.query("repertory", columns, "id = " + id, null, null, null, null);

            while (cursor.moveToNext()) {
                musicRate = cursor.getFloat(cursor.getColumnIndex(column));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            return musicRate;
        }
    }


    public void onPrevClick(View v) {
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
