package com.davidmoodie.compmathsreference;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import us.feras.mdv.MarkdownView;

public class ChapterContent extends AppCompatActivity {

    MarkdownView markdownView;
    int chapterID;

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

        markdownView = (MarkdownView) findViewById(R.id.markdownView);
        markdownView.loadMarkdown("## Hello Markdown");
    }

}
