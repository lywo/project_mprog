package com.example.lydia.savethenight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {
    QuestionAdapter myQuestionAdapter;
    DBhelper myQuestionDBHelper;
    ArrayList<String> favouriteQuestions;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    TextView currentQuestion ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // get favourite questions and fill ListView
        myQuestionDBHelper = new DBhelper(this);
        final ArrayList<String> favouriteQuestions = myQuestionDBHelper.getFavouriteQuestions();
        myQuestionAdapter =  new QuestionAdapter(this, favouriteQuestions);
        final ListView favQuestionLV = (ListView) findViewById(R.id.favouriteQuestionsLV);
        favQuestionLV.setAdapter(myQuestionAdapter);

        // get all questions and fill TextView
        currentQuestion=(TextView) findViewById(R.id.questionTV);
        ArrayList questions = getIntent().getStringArrayListExtra("questionsArrayList");
        assert currentQuestion != null;
        int randomNum=getRandomInt();
        String selectedQuestion = questions.get(randomNum).toString();
        currentQuestion.setText(selectedQuestion);

        favQuestionLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast to let user know item was deleted
                Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                String oldQuestion = favQuestionLV.getItemAtPosition(position).toString();

                // Delete old weather info
                myQuestionDBHelper.setBoolFalse(oldQuestion);

                // Update ListView
                myQuestionAdapter.notifyDataSetChanged();
                return true;
            }
        });

    }

    protected void addQuestion(View view){
        ListView favouriteQuestionsLV = (ListView) findViewById(R.id.favouriteQuestionsLV);
        currentQuestion = (TextView) findViewById(R.id.questionTV);
        assert currentQuestion != null;
        String newFavourite = currentQuestion.getText().toString();
        myQuestionDBHelper.setBoolTrue(newFavourite);
        favouriteQuestions = myQuestionDBHelper.getFavouriteQuestions();
        if(favouriteQuestionsLV != null){favouriteQuestionsLV.setAdapter(myQuestionAdapter);}
        else{
            myQuestionAdapter = new QuestionAdapter(this, favouriteQuestions);
        }
        favouriteQuestionsLV.setAdapter(myQuestionAdapter);


    }

    protected void nextQuestion(View view){
        currentQuestion = (TextView) findViewById(R.id.questionTV);
        ArrayList questions = getIntent().getStringArrayListExtra("questionsArrayList");
        assert currentQuestion != null;
        int randomNum=getRandomInt();
        String selectedQuestion = questions.get(randomNum).toString();
        currentQuestion.setText(selectedQuestion);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event)
//    {
//        switch(event.getAction())
//        {
//            case MotionEvent.ACTION_DOWN:
//                x1 = event.getX();
//                break;
//            case MotionEvent.ACTION_UP:
//                x2 = event.getX();
//                float deltaX = x2 - x1;
//                if (Math.abs(deltaX) > MIN_DISTANCE)
//                {
//                    currentQuestion = (TextView) findViewById(R.id.questionTV);
//                    currentQuestion.setText(getRandomInt());
//                }
//                else
//                {
//                    // consider as something else - a screen tap for example
//                }
//                break;
//        }
//        return super.onTouchEvent(event);
//    }

    public int getRandomInt(){
        Random rand = new Random();
        int randomNum = rand.nextInt((100) + 1);
        return randomNum;
    }
}
