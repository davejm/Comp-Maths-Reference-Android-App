package com.davidmoodie.compmathsreference;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChapterContent extends AppCompatActivity {
    private DataBaseHelper myDbHelper = new DataBaseHelper(this);
    private WebView notesWebView;
    private int chapterID;
    private boolean hasQuiz = false;
    private ArrayList<Integer> questionIDs;

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
        questionIDs = getQuestionIDs();
        Log.d("DEBUG", "onCreate: " + questionIDs.toString());
        if (!questionIDs.isEmpty()) {
            hasQuiz = true;
        }
    }

    public ArrayList<Integer> getQuestionIDs() {
        ArrayList<Integer> questionIDs = new ArrayList<>();
        Cursor cursor = myDbHelper.selectQuery("SELECT _id FROM chapter_question WHERE chapter_id=" + chapterID + " ;");
        if (cursor.moveToFirst()) {
            do {
                questionIDs.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questionIDs;
    }

    public String getChapterHTML(int id) {
        Cursor cursor = myDbHelper.selectQuery("SELECT markdown FROM chapter WHERE _id=" + id + " ;");
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }else {
            return "### These aren't the markdowns you're looking for\n There was an error retrieving requested chapter content";
        }
    }

    private void openChapterQuiz() {
        Intent i = new Intent(getBaseContext(), QuestionActivity.class);
        i.putExtra("chapterID", chapterID);
        i.putExtra("questionIDs", questionIDs);
        i.putExtra("questionIndex", 0);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (hasQuiz) {
            getMenuInflater().inflate(R.menu.menu_chapter, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_quiz) {
            openChapterQuiz();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
