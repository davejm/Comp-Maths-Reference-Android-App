package com.davidmoodie.compmathsreference;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    private DataBaseHelper myDbHelper = new DataBaseHelper(this);
    private WebView questionWebView;
    private int chapterID;
    private ArrayList<Integer> questionIDs;
    private int questionID;
    private int questionIndex;

    private String questionHTML;
    private String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            chapterID = extras.getInt("chapterID");
            questionIDs = extras.getIntegerArrayList("questionIDs");
            questionIndex = extras.getInt("questionIndex");
            questionID = questionIDs.get(questionIndex);
        }

        myDbHelper.openDataBase();
        questionWebView = (WebView) findViewById((R.id.questionWebView));
        getQuestionContent();
        questionWebView.loadData(questionHTML, "text/html", null);
    }

    public void getQuestionContent() throws NullPointerException {
        Cursor cursor = myDbHelper.selectQuery("SELECT question, answer FROM chapter_question WHERE _id=" + questionID + " ;");
        if (cursor.moveToFirst()) {
            questionHTML = cursor.getString(0);
            answer = cursor.getString(1);
        } else {
            throw new NullPointerException("Cannot find question content for question ID = " + questionID);
        }
    }

}
