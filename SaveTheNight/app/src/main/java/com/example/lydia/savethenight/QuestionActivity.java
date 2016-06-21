/*
Lydia Wolfs
QuestionActivity.java called from MainActivity
 */
package com.example.lydia.savethenight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

/*
Set Text in TextView with random picked question and update onSwipe
Fill ListView with favourite Questions
Allow favourite questions to be added by clicking on  + icon
 */
public class QuestionActivity extends AppCompatActivity {
    QuestionAdapter myQuestionAdapter;
    DBhelper myQuestionDBHelper;
    TextView currentQuestion ;
    int randomNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Fill ListView with all the favourite questions
        myQuestionDBHelper = new DBhelper(this);
        final ArrayList<String> favouriteQuestions = myQuestionDBHelper.getFavouriteQuestions();
        myQuestionAdapter =  new QuestionAdapter(this, favouriteQuestions);
        final ListView favQuestionLV = (ListView) findViewById(R.id.favouriteQuestionsLV);
        assert favQuestionLV != null;
        favQuestionLV.setAdapter(myQuestionAdapter);

        // Load all questions
        currentQuestion=(TextView) findViewById(R.id.questionTV);
        final ArrayList questions = getIntent().getStringArrayListExtra("questionsArrayList");

        // Select one random questions from all questions and fill TextView
        randomNum=getRandomInt();
        String selectedQuestion = questions.get(randomNum).toString();
        currentQuestion.setText(selectedQuestion);

        /*
        onSwipe show new random question from list.
        Both right and left swipes are allowed
         */
        assert currentQuestion != null;
        currentQuestion.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                if (randomNum >= 2){ // Go to previous question
                    randomNum --;
                }
                else{ // Go back to the end of the list
                    randomNum = 101;
                }
                // Select question from database on individual id number and fill in TextView
                String selectedQuestion = myQuestionDBHelper.getOneQuestion(randomNum);
                currentQuestion.setText(selectedQuestion);
            }

            public void onSwipeRight(){
                if (randomNum <= 100){ // Go to next question in list
                    randomNum ++;
                }
                else{ // start at the begin
                    randomNum = 1;
                }

                // Select question from database on individual id number and fill in TextView
                String selectedQuestion = myQuestionDBHelper.getOneQuestion(randomNum);
                currentQuestion.setText(selectedQuestion);
            }
        });

        favQuestionLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast to let user know item was deleted
                Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                String oldQuestion = favQuestionLV.getItemAtPosition(position).toString();

                // Remove questions from favourites, by calling function from DBhelper
                myQuestionDBHelper.setBoolFalse(oldQuestion);

                // Update the favourites list
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
    Save current question in TextView as a favourite question
    Update the ListView
     */
    protected void addQuestion(View view){
        ListView favouriteQuestionsLV = (ListView) findViewById(R.id.favouriteQuestionsLV);
        currentQuestion = (TextView) findViewById(R.id.questionTV);

        assert currentQuestion != null;
        String newFavourite = currentQuestion.getText().toString();

        // Update the current quesition as a favourite by calling function from DBhelper
        myQuestionDBHelper.setBoolTrue(newFavourite);

        // Make new ArrayList of current favourite questions
        ArrayList<String> favouriteQuestions = myQuestionDBHelper.getFavouriteQuestions();
        myQuestionAdapter =  new QuestionAdapter(QuestionActivity.this, favouriteQuestions);

        // Update ListView
        assert favouriteQuestionsLV != null;
        favouriteQuestionsLV.setAdapter(myQuestionAdapter);
        myQuestionAdapter.notifyDataSetChanged();
    }

    /*
    Calculate random int between 0 and 100
    Return integer
     */
    public int getRandomInt(){
        Random rand = new Random();
        return rand.nextInt((100) + 1);
    }
}
