package com.example.myrepertory;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;


public class UpDeleteActivity extends Activity {
    private EditText name, artist, genre, data, dam, joy;
    private RatingBar singable, priority;
    private RelativeLayout back;
    private SQLiteDatabase db;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_up_delete);

        DBOpenHelper dbHelper = new DBOpenHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 1);

        back = (RelativeLayout) this.findViewById(R.id.back4);

        name = (EditText) findViewById(R.id.nameEdit2);
        name.setText(searchData(id, "name"));
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        artist = (EditText) this.findViewById(R.id.artistEdit2);
        artist.setText(searchData(id, "artist"));
        artist.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        genre = (EditText) this.findViewById(R.id.genreEdit2);
        genre.setText(searchData(id, "genre"));
        genre.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        data = (EditText) this.findViewById(R.id.musicEdit2);
        data.setText(searchData(id, "data"));
        data.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        dam = (EditText) this.findViewById(R.id.damEdit2);
        dam.setText(searchData(id, "dam"));
        dam.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        joy = (EditText) this.findViewById(R.id.joyEdit2);
        joy.setText(searchData(id, "joy"));
        joy.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

        singable = (RatingBar) findViewById(R.id.singableRate2);
        singable.setRating(searchRate(id, "singable"));
        priority = (RatingBar) findViewById(R.id.priorityRate2);
        priority.setRating(searchRate(id, "priority"));
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


    public void update(SQLiteDatabase db) {
        String replaceText;
        ContentValues val = new ContentValues();
        replaceText = Check(name.getText().toString());
        val.put("name", replaceText);
        replaceText = Check(artist.getText().toString());
        val.put("artist", replaceText);
        replaceText = Check(genre.getText().toString());
        val.put("genre", replaceText);
        replaceText = Check(data.getText().toString());
        val.put("data", replaceText);
        val.put("dam", dam.getText().toString());
        val.put("joy", joy.getText().toString());
        val.put("singable", singable.getRating());
        val.put("priority", priority.getRating());

        db.update("repertory", val, "id = " + id, null);
    }

    public void onUpdateClick(View v) {
        boolean update = true;
        if (name.getText().toString().length() == 0) {    //曲名がnullの場合
            update = false;
        }
        if (artist.getText().toString().length() == 0) {  //アーティスト名がnullの場合
            update = false;
        }
        float point;
        if (dam.getText().toString().length() != 0) {
            point = Float.parseFloat(dam.getText().toString());
            if ((point > 100) || (point < 0)) {  //DAMの点数が100点以降もしくは0点未満の場合
                update = false;
            }
        }
        if (joy.getText().toString().length() != 0) {
            point = Float.parseFloat(joy.getText().toString());
            if ((point > 100) || (point < 0)) {  //JOYの点数が100点以降もしくは0点未満の場合
                update = false;
            }
        }
        if (update) {
            update(db);
            db.close();
            finish();
        }
    }

    public void onDeleteClick(View v){
        db.delete("repertory", "id = " + id, null);
        finish();
    }

    public void onPrevClick(View v) {
        finish();
    }

    public void onBackClick(View v) {
        back.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_up_delete, menu);
        return true;
    }

    private String Check(String line) {
        String result = line;
        if (line.indexOf("'") != -1) {
            result = StringReplace(line, "'", "''");
        }
        return result;
    }

    private String StringReplace(String line, String prev, String next) {
        StringBuffer buffer = new StringBuffer(line);
        int start;
        while ((start = buffer.toString().indexOf(prev)) != -1) {
            buffer.replace(start, start + prev.length(), next);
        }
        return buffer.toString();
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
