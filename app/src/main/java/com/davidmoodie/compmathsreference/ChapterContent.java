package com.davidmoodie.compmathsreference;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import us.feras.mdv.MarkdownView;

public class ChapterContent extends AppCompatActivity {
    private DataBaseHelper myDbHelper = new DataBaseHelper(this);
    private MarkdownView markdownView;
    private int chapterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            chapterID = extras.getInt("chapterID");
            Toast.makeText(ChapterContent.this, "Chapter ID = " + chapterID, Toast.LENGTH_SHORT).show();
        }

        myDbHelper.openDataBase();
        markdownView = (MarkdownView) findViewById(R.id.markdownView);
        markdownView.loadMarkdown(this.getChapterMarkdown(chapterID));
    }

    public String getChapterMarkdown(int id) {
        Cursor cursor = myDbHelper.selectQuery("SELECT markdown FROM chapter WHERE _id=" + id + " ;");
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }else {
            return "### These aren't the markdown your looking for :(";
        }
    }
}
