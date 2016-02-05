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
        showQuestion();
    }

    private void getQuestionContent() throws NullPointerException {
        Cursor cursor = myDbHelper.selectQuery("SELECT question, answer FROM chapter_question WHERE _id=" + questionID + " ;");
        if (cursor.moveToFirst()) {
            questionHTML = cursor.getString(0);
            answer = cursor.getString(1);
            Toast.makeText(QuestionActivity.this, "Answer is: " + answer, Toast.LENGTH_SHORT).show();
        } else {
            throw new NullPointerException("Cannot find question content for question ID = " + questionID);
        }
    }

    private void showQuestion() {
        getQuestionContent();
        questionWebView.loadData(questionHTML, "text/html", null);
    }

    private void isAnswerCorrect(String ans) {
        if ( ans.equalsIgnoreCase(answer) ) {
            Toast.makeText(QuestionActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            questionIndex++;
            try {
                questionID = questionIDs.get(questionIndex);
                getQuestionContent();
                showQuestion();
            } catch (IndexOutOfBoundsException e) {
                Toast.makeText(QuestionActivity.this, "No more questions to answer.", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(QuestionActivity.this, "Incorrect :(", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonA:
                isAnswerCorrect("a");
                break;
            case R.id.buttonB:
                isAnswerCorrect("b");
                break;
            case R.id.buttonC:
                isAnswerCorrect("c");
                break;
            case R.id.buttonD:
                isAnswerCorrect("d");
                break;
        }
    }

}
