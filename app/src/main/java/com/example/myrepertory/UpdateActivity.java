package com.example.myrepertory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class UpdateActivity extends Activity {
    private String[] search = {"曲名", "アーティスト", "ジャンル", "曲情報"};
    private String[] sort = {"曲名", "アーティスト", "ジャンル", "DAM点数", "JOY点数", "歌いやすさ", "優先度"};
    private SQLiteDatabase db;
    private EditText searchEdit;
    private Spinner searchSpin, sortSpin;
    private RelativeLayout back;
    private ListView nameList;

    public void displayList() {     //listにデータを表示する
        nameList = (ListView) this.findViewById(R.id.listView2);
        ArrayList<Music> list = new ArrayList<Music>();
        Cursor cursor = null;
        String[] columns = {"id", "name", "artist"};
        try {
            cursor = db.query(false, "repertory", columns, null, null, null, null, "name asc, artist asc", null);
            int indexId = cursor.getColumnIndex("id");
            int indexName = cursor.getColumnIndex("name");
            int indexArtist = cursor.getColumnIndex("artist");
            int id;
            String name;
            String artist;

            ArrayAdapter<Music> adapter = new ArrayAdapter<Music>(this, android.R.layout.simple_expandable_list_item_1, list);
            while (cursor.moveToNext()) {
                id = cursor.getInt(indexId);
                name = cursor.getString(indexName);
                artist = cursor.getString(indexArtist);
                adapter.add(new Music(id, name, artist));
            }
            nameList.setAdapter(adapter);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void searchWork(int position) {
        boolean edit;
        int searchNum = (int) searchSpin.getSelectedItemPosition();
        String searchColumn = "";
        if (searchEdit.getText().toString().length() == 0) {
            edit = false;
        } else {
            edit = true;
        }
        if (searchNum == 0) {
            searchColumn = "name";
        } else if (searchNum == 1) {
            searchColumn = "artist";
        } else if (searchNum == 2) {
            searchColumn = "genre";
        } else if (searchNum == 3) {
            searchColumn = "data";
        }

        String search = searchColumn + " LIKE '%" + searchEdit.getText().toString() + "%' ESCAPE '@'";
        if (position == 0) {
            searchSort(search, "name asc, artist asc", edit);
        } else if (position == 1) {
            searchSort(search, "artist asc, name asc", edit);
        } else if (position == 2) {
            searchSort(search, "genre = '' asc, genre asc, name asc, artist asc", edit);
        } else if (position == 3) {
            searchSort(search, "dam desc, name asc, artist asc", edit);
        } else if (position == 4) {
            searchSort(search, "joy desc, name asc, artist asc", edit);
        } else if (position == 5) {
            searchSort(search, "singable desc, name asc, artist asc", edit);
        } else if (position == 6) {
            searchSort(search, "priority desc, name asc, artist asc", edit);
        }
    }

    public void searchSort(String search, String orderBy, boolean edit) {
        ListView nameList = (ListView) this.findViewById(R.id.listView2);
        ArrayList<Music> list = new ArrayList<Music>();
        Cursor cursor = null;
        try {
            if (edit) {
                cursor = db.query(false, "repertory", null, search, null, null, null, orderBy, null);
            } else {
                cursor = db.query(false, "repertory", null, null, null, null, null, orderBy, null);
            }
            int indexId = cursor.getColumnIndex("id");
            int indexName = cursor.getColumnIndex("name");
            int indexArtist = cursor.getColumnIndex("artist");
            int id;
            String name;
            String artist;

            ArrayAdapter<Music> adapter = new ArrayAdapter<Music>(this, android.R.layout.simple_expandable_list_item_1, list);
            while (cursor.moveToNext()) {
                id = cursor.getInt(indexId);
                name = cursor.getString(indexName);
                artist = cursor.getString(indexArtist);
                adapter.add(new Music(id, name, artist));
            }
            nameList.setAdapter(adapter);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_update);

        DBOpenHelper dbHelper = new DBOpenHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        back = (RelativeLayout) this.findViewById(R.id.back3);

        searchSpin = (Spinner) this.findViewById(R.id.spinner3);
        ArrayAdapter<String> searchAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, search);
        searchSpin.setAdapter(searchAdapter);

        sortSpin = (Spinner) this.findViewById(R.id.spinner4);
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sort);
        sortSpin.setAdapter(sortAdapter);

        searchEdit = (EditText) this.findViewById(R.id.editText2);

        displayList();
        nameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ListView listView = (ListView) parent;
                // クリックされたアイテムを取得します
                Music m = (Music) listView.getItemAtPosition(position);

                Intent intent = new Intent(UpdateActivity.this, UpDeleteActivity.class);
                intent.putExtra("id", m.getId());
                startActivity(intent);
            }
        });


        searchEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false) {      //検索ワードを入力し終えたときに検索＆ソート
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    searchWork((int) sortSpin.getSelectedItemPosition());
                }
            }
        });
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //テキストフィールドに入力した際の検索処理
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchWork((int) searchSpin.getSelectedItemPosition());
                    return true;
                } else {
                    return false;
                }
            }
        });


        searchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //ソートの要素が選択されたときのソート処理
                searchWork((int) searchSpin.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //処理なし
            }
        });

        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //ソートの要素が選択されたときのソート処理
                searchWork(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //処理なし
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        // データの編集・削除後にリストを更新する
        searchWork((int) sortSpin.getSelectedItemPosition());
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
        getMenuInflater().inflate(R.menu.menu_update, menu);
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
