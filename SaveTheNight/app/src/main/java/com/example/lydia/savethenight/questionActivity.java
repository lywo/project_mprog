package com.example.lydia.savethenight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {
    QuestionAdapter myQuestionAdapter;
    DBhelper myQuestionDBHelper;
    ArrayList<String> favouriteQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        TextView currentQuestion = (TextView) findViewById(R.id.questionTV);
        myQuestionDBHelper = new DBhelper(this);
        String [] questions = getIntent().getStringArrayExtra("questionsArray");
        ArrayList<String> favouriteQuestions = myQuestionDBHelper.getFavouriteQuestions();
        myQuestionAdapter =  new QuestionAdapter(this, favouriteQuestions);
        Random rand = new Random();
        int randomNum = rand.nextInt((100) + 1);
        assert currentQuestion != null;
        currentQuestion.setText(questions[randomNum]);
    }

    protected void addQuestion(){
        ListView favouriteQuestionsLV = (ListView) findViewById(R.id.favouriteQuestionsLV);
        TextView currentQuestion = (TextView) findViewById(R.id.questionTV);
        assert currentQuestion != null;
        String newFavourite = currentQuestion.getText().toString();
        myQuestionDBHelper.setBoolTrue(newFavourite);
        favouriteQuestions = myQuestionDBHelper.getFavouriteQuestions();
        if(favouriteQuestionsLV != null){favouriteQuestionsLV.setAdapter(myQuestionAdapter);}
        else{
            myQuestionAdapter = new QuestionAdapter(this, favouriteQuestions);
        }
    }
}
