package com.example.myrepertory;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
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


public class InsertActivity extends Activity {
    private EditText name, artist, genre, data, dam, joy;
    private RatingBar singable, priority;
    private RelativeLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_insert);

        name = (EditText) findViewById(R.id.nameEdit);

        back = (RelativeLayout) this.findViewById(R.id.back2);

        EditText nameEdit = name;
        nameEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        EditText artistEdit = (EditText) this.findViewById(R.id.artistEdit);
        artistEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        EditText genreEdit = (EditText) this.findViewById(R.id.genreEdit);
        genreEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        EditText musicEdit = (EditText) this.findViewById(R.id.musicEdit);
        musicEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        EditText damEdit = (EditText) this.findViewById(R.id.damEdit);
        damEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        EditText joyEdit = (EditText) this.findViewById(R.id.joyEdit);
        joyEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }

    public void insert(SQLiteDatabase db) {
        ContentValues val = new ContentValues();
        val.put("name", name.getText().toString());
        val.put("artist", artist.getText().toString());
        if (genre.getText().toString().length() != 0) {
            val.put("genre", genre.getText().toString());
        }
        if (data.getText().toString().length() != 0) {
            val.put("data", data.getText().toString());
        }
        if (dam.getText().toString().length() != 0) {
            val.put("dam", dam.getText().toString());
        }
        if (joy.getText().toString().length() != 0) {
            val.put("joy", joy.getText().toString());
        }
        val.put("singable", singable.getRating());
        val.put("priority", priority.getRating());

        db.insert("repertory", null, val);
    }

    public void onInsertClick(View v) {
        boolean insert = true;
        if (name.getText().toString().length() == 0) {    //曲名がnullの場合
            insert = false;
        }
        artist = (EditText) findViewById(R.id.artistEdit);
        if (artist.getText().toString().length() == 0) {  //アーティスト名がnullの場合
            insert = false;
        }
        genre = (EditText) findViewById(R.id.genreEdit);
        data = (EditText) findViewById(R.id.musicEdit);
        dam = (EditText) findViewById(R.id.damEdit);
        float point;
        if (dam.getText().toString().length() != 0) {
            point = Float.parseFloat(dam.getText().toString());
            if ((point > 100) || (point < 0)) {  //DAMの点数が100点以降もしくは0点未満の場合
                insert = false;
            }
        }
        joy = (EditText) findViewById(R.id.joyEdit);
        if (joy.getText().toString().length() != 0) {
            point = Float.parseFloat(joy.getText().toString());
            if ((point > 100) || (point < 0)) {  //JOYの点数が100点以降もしくは0点未満の場合
                insert = false;
            }
        }
        singable = (RatingBar) findViewById(R.id.singableRate);
        priority = (RatingBar) findViewById(R.id.priorityRate);

        if (insert) {
            DBOpenHelper dbHelper = new DBOpenHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            insert(db);
            db.close();
            finish();
        }
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
        getMenuInflater().inflate(R.menu.menu_insert, menu);
        return true;
    }

    // 置換いらなかった
    /*private String Check(String line) {
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
    }*/

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
