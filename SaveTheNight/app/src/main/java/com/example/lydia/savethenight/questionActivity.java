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
        final ArrayList questions = getIntent().getStringArrayListExtra("questionsArrayList");
        int randomNum=getRandomInt();
        String selectedQuestion = questions.get(randomNum).toString();
        currentQuestion.setText(selectedQuestion);

        /*
        onSwipe show new random question from list.
        Both right and left swipes are alowed
         */
        assert currentQuestion != null;
        currentQuestion.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                int randomNum=getRandomInt();
                String selectedQuestion = questions.get(randomNum).toString();
                currentQuestion.setText(selectedQuestion);
            }

            public void onSwipeRight(){
                int randomNum=getRandomInt();
                String selectedQuestion = questions.get(randomNum).toString();
                currentQuestion.setText(selectedQuestion);
            }
        });

        favQuestionLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast to let user know item was deleted
                Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                String oldQuestion = favQuestionLV.getItemAtPosition(position).toString();

                // Set question to non favourite, find new List and set Adapter
                myQuestionDBHelper.setBoolFalse(oldQuestion);
                ArrayList<String> favouriteQuestions = myQuestionDBHelper.getFavouriteQuestions();
                myQuestionAdapter =  new QuestionAdapter(QuestionActivity.this, favouriteQuestions);
                favQuestionLV.setAdapter(myQuestionAdapter);

                // Update ListView
                myQuestionAdapter.notifyDataSetChanged();
                return true;
            }
        });

    }

    /*
    Method on Button click add question to favourites (Boolean reset to true)
    Update the ListView
     */
    protected void addQuestion(View view){
        ListView favouriteQuestionsLV = (ListView) findViewById(R.id.favouriteQuestionsLV);
        currentQuestion = (TextView) findViewById(R.id.questionTV);
        assert currentQuestion != null;
        String newFavourite = currentQuestion.getText().toString();
        myQuestionDBHelper.setBoolTrue(newFavourite);
        ArrayList<String> favouriteQuestions = myQuestionDBHelper.getFavouriteQuestions();
        myQuestionAdapter =  new QuestionAdapter(QuestionActivity.this, favouriteQuestions);
        favouriteQuestionsLV.setAdapter(myQuestionAdapter);

        // Update ListView
        myQuestionAdapter.notifyDataSetChanged();
    }

//    protected void nextQuestion(View view){
//        currentQuestion = (TextView) findViewById(R.id.questionTV);
//        ArrayList questions = getIntent().getStringArrayListExtra("questionsArrayList");
//        assert currentQuestion != null;
//        int randomNum=getRandomInt();
//        String selectedQuestion = questions.get(randomNum).toString();
//        currentQuestion.setText(selectedQuestion);
//    }


    public int getRandomInt(){
        Random rand = new Random();
        int randomNum = rand.nextInt((100) + 1);
        return randomNum;
    }
}
