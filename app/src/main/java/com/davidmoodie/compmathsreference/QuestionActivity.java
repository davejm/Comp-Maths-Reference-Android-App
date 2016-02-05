package com.davidmoodie.compmathsreference;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonA:
                Toast.makeText(QuestionActivity.this, "buttonA", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonB:
                Toast.makeText(QuestionActivity.this, "buttonB", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonC:
                Toast.makeText(QuestionActivity.this, "buttonC", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonD:
                Toast.makeText(QuestionActivity.this, "buttonD", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
