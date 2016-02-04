package com.davidmoodie.compmathsreference;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.Toast;

public class ChapterContent extends AppCompatActivity {
    private DataBaseHelper myDbHelper = new DataBaseHelper(this);
    private WebView notesWebView;
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
        notesWebView = (WebView) findViewById(R.id.notesWebView);
        notesWebView.loadData(getChapterHTML(chapterID), "text/html", null);
    }

    public String getChapterHTML(int id) {
        Cursor cursor = myDbHelper.selectQuery("SELECT markdown FROM chapter WHERE _id=" + id + " ;");
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }else {
            return "### These aren't the markdowns you're looking for\n There was an error retrieving requested chapter content";
        }
    }
}
