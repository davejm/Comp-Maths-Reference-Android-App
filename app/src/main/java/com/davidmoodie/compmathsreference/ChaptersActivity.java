package com.davidmoodie.compmathsreference;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ChaptersActivity extends AppCompatActivity {
    private ArrayList<IDString> chapters;
    private ArrayAdapter<IDString> chaptersAdapter;
    private ListView lvChapters;
    private int topicID;

    private DataBaseHelper myDbHelper = new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDbHelper.openDataBase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            topicID = extras.getInt("topicID");
            //textView.setText(topicID + " " + textView.getText());
        }
        populateChaptersListView();
        setupListViewListener();
    }

    public void populateChaptersListView() {
        lvChapters = (ListView) findViewById(R.id.lvChapters);
        chapters = new ArrayList<IDString>();
        chaptersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chapters);
        lvChapters.setAdapter(chaptersAdapter);
        Cursor cursor = myDbHelper.selectQuery("SELECT _id, name FROM chapter WHERE topic_id=" + topicID + " ;");
        if (cursor.moveToFirst()) {
            do {
                chapters.add(new IDString(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void setupListViewListener() {
        lvChapters.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                            View item, int pos, long id) {
                        Intent i = new Intent(getBaseContext(), ChapterContent.class);
                        i.putExtra("chapterID", chapters.get(pos).getID());
                        startActivity(i);
                    }

                });
    }
}
