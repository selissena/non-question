package com.example.sena.picture_quiz;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import android.graphics.Typeface;


public class ViewQuestions extends AppCompatActivity {
    /* Global vars */
    // Tag for Logging
    static final String TAG = "MainActivity Class: ";
    // Total number of questions
    static final int QUIZ_COUNT = 6;
    // List of definitions the user answered for
    static ArrayList<Question> askedDefinitions = new ArrayList<>();

    // Current quiz number's 4 questions being displayed

    static ArrayList<Integer> imageLinkHolder = new ArrayList<>();


    TextView tvQuizNumber, tvDefinition;
    //Button bnext;09.07
    Button bReplay, bReplayQuiz;
    ImageButton image1, image2, image3, image4;


    //ArrayList<ArrayList<Question>> questions = new ArrayList<>();
    ArrayList<Question> questions = new ArrayList<>();
    ArrayList<Question> questions_easy = new ArrayList<>();
    ArrayList<Question> questions_hard = new ArrayList<>();
    //ArrayList<Question> currQuestions = new ArrayList<>();

    Question currQuestion;

    int quizNumber = 1, position = 1;


    boolean answeredCorrectly = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_questions);
        //------------------------------------------------------------25.07---------------

        TextView tx = (TextView)findViewById(R.id.tvDefinition);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/3.ttf");
        tx.setTypeface(custom_font);
        //-------------------------------------------------------------------------------

        Log.d(TAG, "onCreate()");

        // Get handles to fields
        tvQuizNumber = (TextView) findViewById(R.id.tvQuizNumber);
        tvDefinition = (TextView) findViewById(R.id.tvDefinition);



        //bNext = (Button) findViewById(R.id.buttonCheckedAnswer);09.07
        bReplay = (Button) findViewById(R.id.buttonReplay);
        bReplayQuiz = (Button) findViewById(R.id.buttonReplayQuiz);


        image1 = (ImageButton) findViewById(R.id.ib1);
        image2 = (ImageButton) findViewById(R.id.ib2);
        image3 = (ImageButton) findViewById(R.id.ib3);
        image4 = (ImageButton) findViewById(R.id.ib4);

        setQuestions();

        if (savedInstanceState != null) {
            // Get the saved values from the Bundle

            quizNumber = savedInstanceState.getInt("quizNumber");
            position = savedInstanceState.getInt("position");
            answeredCorrectly = savedInstanceState.getBoolean("answeredCorrectly");
            currQuestion = questions.get(savedInstanceState.getInt("currQuestionIndex"));

            /*
            if(level.equals("easy")) {
                currQuestion = questions_easy.get(savedInstanceState.getInt("currQuestionIndex"));
            }else{//if(level.equals("hard"))
                currQuestion = questions_hard.get(savedInstanceState.getInt("currQuestionIndex"));
            }
            */


            // Make sure no previously asked questions are used again

            restoreDisplay(savedInstanceState);

        } else {


            // Initialize layout
            currQuestion = getCurrQuestion();
            tvDefinition.setText(currQuestion.getDefinition());
            tvQuizNumber.setText(String.valueOf(quizNumber));

            // Prepare and display images

            displayImages();

           

        }


    }

    /**
     * If the user has reached the end of the quiz,
     * display the replay button and hide the next
     * button.
     */


    /*
    private void checkReplay() {
        // If the user has reached the end of the quiz, offer to replay
        if (quizNumber == QUIZ_COUNT) {
            bReplayQuiz.setVisibility(View.VISIBLE);
            //bNext.setVisibility(View.GONE);09.07
        } else {
            // Enable next button//bNext.setVisibility(View.VISIBLE); bNext.setEnabled(true); 09.07

            next();


        }
    }
    */

    private void checkNext() {
        // If the user has reached the end of the quiz, offer to replay

        /*
        if (quizNumber == QUIZ_COUNT) {
            bReplayQuiz.setVisibility(View.VISIBLE);
        }else{
            next();
        }
        */


        if (quizNumber != QUIZ_COUNT) {
            bReplayQuiz.setVisibility(View.VISIBLE);
            next();
        }else{
            bReplayQuiz.setVisibility(View.VISIBLE);

        }



    }

    public void next(){


        setImagesClickable(false);

        // Reset counters and arrays

        answeredCorrectly = false;

        if(quizNumber<3){ //if(askedDefinitions.size()<6)
            questions = questions_easy;
        }else{
            questions = questions_hard;
        }
        // questionsHolder.clear();

        // Get new questions
        currQuestion = getCurrQuestion();

        // Set display and clickables
        displayImages();
        setImagesClickable(true);


        quizNumber++;

        // Set text for quiz and definition
        tvQuizNumber.setText(String.valueOf(quizNumber));
        tvDefinition.setText(currQuestion.getDefinition());



    }
    /**
     * Rebuild the display.
     *
     * @param savedInstanceState
     */
    private void restoreDisplay(Bundle savedInstanceState) {
        // Set text views
        tvDefinition.setText(currQuestion.getDefinition());
        tvQuizNumber.setText(String.valueOf(quizNumber));


        // Restore images
        image1.setImageResource(currQuestion.getImageLink());
        image2.setImageResource(currQuestion.imageLink2);
        image3.setImageResource(currQuestion.imageLink3);
        image4.setImageResource(currQuestion.imageLink4);



        // If the user previously answered the question correctly,
        // prevent the user from clicking the images and restore their choice
        if (answeredCorrectly) {
            // Display a checkmark image on the image button
            // the user was able to properly guess
            switch (position) {
                case 1:
                    image1.setImageResource(R.drawable.correct);
                    break;
                case 2:
                    image2.setImageResource(R.drawable.correct);
                    break;
                case 3:
                    image3.setImageResource(R.drawable.correct);
                    break;
                case 4:
                    image4.setImageResource(R.drawable.correct);
                    break;
                default:
                    break;
            }

            setImagesClickable(false);

            checkNext();
        } else { // user has not answered the question correctly

            // prevent the user from clicking the images and restore their choices

            // Show which image is the correct one


            // Disable all images
            setImagesClickable(false);




        }
    }

    /**
     * Initiates the questions array with road sign
     * images from res/drawable if the questions array
     * is empty
     */
    private void setQuestions() {
        if (questions_easy.isEmpty()) {

            questions_easy.add(new Question(R.drawable.kargaburnu, getResources().getString(R.string.definitionKargaburnu),R.drawable.testere, R.drawable.cekic, R.drawable.tornavida));
            questions_easy.add(new Question(R.drawable.avakado, getResources().getString(R.string.definitionAvakado),R.drawable.incir, R.drawable.karpuz, R.drawable.cilek));
            questions_easy.add(new Question(R.drawable.franksinatra, getResources().getString(R.string.definitionFrankSinatra),R.drawable.elvispresley, R.drawable.bobmarley, R.drawable.louisarmstrong));


            /*
            questions_easy.add(new Question(R.drawable.red, getResources().getString(R.string.definitionRed),R.drawable.yellow, R.drawable.blue, R.drawable.pink));
            questions_easy.add(new Question(R.drawable.yellow, getResources().getString(R.string.definitionYellow),R.drawable.purple,R.drawable.grey,R.drawable.blue));
            questions_easy.add(new Question(R.drawable.blue, getResources().getString(R.string.definitionBlue),R.drawable.pink,R.drawable.yellow,R.drawable.red));
            */


        }
        if (questions_hard.isEmpty()){

            questions_hard.add(new Question(R.drawable.queen, getResources().getString(R.string.definitionQueen),R.drawable.ledzeppelin,R.drawable.deeppurple,R.drawable.beatles));
            questions_hard.add(new Question(R.drawable.abbey_road, getResources().getString(R.string.definitionAbbeyRoad),R.drawable.let_it_be,R.drawable.a_hard_days_night,R.drawable.help));
            questions_hard.add(new Question(R.drawable.lale, getResources().getString(R.string.definitionLale),R.drawable.kaktus, R.drawable.papatya, R.drawable.gul));

            //questions_hard.add(new Question(R.drawable.krikettopu, getResources().getString(R.string.definitionKriketTopu),R.drawable.futboltopu,R.drawable.voleyboltopu,R.drawable.basketboltopu));


        }

        questions = questions_easy;
    }

    /**
     * Gets the current question that the
     * user will have to answer
     *
     * @return {Question} question
     */

    private Question getCurrQuestion() {
        Log.i(TAG, "getCurrQuestion()");

        // Get a random question from available questions
        Random random = new Random();
        int randomIndex = random.nextInt(questions.size());

        Question randomQuestion = questions.get(randomIndex);

        // Make sure this question hasn't been asked before
        while (askedDefinitions.contains(randomQuestion)) {
            randomIndex = random.nextInt(questions.size());
            randomQuestion = questions.get(randomIndex);
        }

        // Add to asked definitions (used for non-repetition on next button click)
        askedDefinitions.add(randomQuestion);
        // Add to asked definitions index (used keep track of used questions between runtimes (see onSaveInstanceState)


        return randomQuestion;
    }


    /**
     * Randomizes and displays the images
     */
    private void displayImages() {
        Log.i(TAG, "Display Images");
        /*

        questionsHolder.addAll(currQuestions);
        questionsHolder.add(currQuestion);
        Collections.shuffle(questionsHolder);
        position = questionsHolder.indexOf(currQuestion) + 1;

        image1.setImageResource(questionsHolder.get(0).getImageLink());
        image2.setImageResource(questionsHolder.get(1).getImageLink());
        image3.setImageResource(questionsHolder.get(2).getImageLink());
        image4.setImageResource(questionsHolder.get(3).getImageLink());

        */
        imageLinkHolder.clear();

        Integer int1 = new Integer(currQuestion.getImageLink());
        Integer int2 = new Integer(currQuestion.imageLink2);
        Integer int3 = new Integer(currQuestion.imageLink3);
        Integer int4 = new Integer(currQuestion.imageLink4);
        imageLinkHolder.add(int1);
        imageLinkHolder.add(int2);
        imageLinkHolder.add(int3);
        imageLinkHolder.add(int4);
        Collections.shuffle(imageLinkHolder);
        position = imageLinkHolder.indexOf(currQuestion.getImageLink())+1;

        image1.setImageResource(imageLinkHolder.get(0));
        image2.setImageResource(imageLinkHolder.get(1));
        image3.setImageResource(imageLinkHolder.get(2));
        image4.setImageResource(imageLinkHolder.get(3));



        /*
        image1.setImageResource(currQuestion.getImageLink());
        image2.setImageResource(currQuestion.imageLink2);
        image3.setImageResource(currQuestion.imageLink3);
        image4.setImageResource(currQuestion.imageLink4);
        */

    }

    /**
     * Handles an image click, determines the position and
     * checks if it corresponds to the correct definition
     *
     * @param view
     */
    public void imageClick(View view) {
        setImagesClickable(false);
        ImageButton selectedImage = (ImageButton) findViewById(view.getId());

        int chosenPosition = Integer.parseInt(getResources().getResourceEntryName(view.getId()).substring(2));

        Log.d(TAG, "Correct position: " + position);


        if (chosenPosition == position) {// Player chose the right definition

            Log.d(TAG, "Correct position was chosen");

            answeredCorrectly = true;


            tvQuizNumber.setText(String.valueOf(quizNumber));

            Log.d(TAG, "Set clickable false");

            // Alter image to show user answer is correct
            selectedImage.setImageResource(R.drawable.correct);

            Log.d(TAG, "Show correct");


            checkNext();



            Log.d(TAG, "Enable next");
        } else {// Player chose the wrong definition

            tvQuizNumber.setText(String.valueOf(quizNumber));


            // Alter image to show user answer is correct
            selectedImage.setImageResource(R.drawable.incorrect);


            // Indicate to user which answer is the correct one


            //bNext.setVisibility(View.GONE);//09.07



        }

        bReplayQuiz.setVisibility(View.VISIBLE);


    }




    /**
     * Handles a next click, displays four new questions
     * and gets a new current question
     *
     * @param view
     */






    /**
     * Handles an replay , restarts the quiz.
     *
     * @param view
     */

    public void replayClick(View view) {//restart the level

        bReplay.setVisibility(View.GONE);
        //bNext.setVisibility(View.INVISIBLE); 09.07
        bReplayQuiz.setVisibility(View.GONE);

        // Rest counters
        quizNumber = 1;
        position = 1;
        answeredCorrectly = false;

        // Reset questions

        askedDefinitions.clear();

        //questionsHolder.clear();

        // Initialize layout

        currQuestion = getCurrQuestion();
        tvDefinition.setText(currQuestion.getDefinition());
        tvQuizNumber.setText(String.valueOf(quizNumber));


        // Prepare and display images

        displayImages();
        setImagesClickable(true);


    }



    /**
     * Handles an replay , restarts the quiz.
     *
     * @param view
     */
    public void replayQuiz(View view) {//restart the quiz

        bReplay.setVisibility(View.GONE);
        //bNext.setVisibility(View.INVISIBLE); 09.07
        bReplayQuiz.setVisibility(View.GONE);

        // Rest counters
        quizNumber = 1;
        position = 1;
        answeredCorrectly = false;

        // Reset questions

        askedDefinitions.clear();

        //questionsHolder.clear();

        // Initialize layout
        questions = questions_easy;
        currQuestion = getCurrQuestion();
        tvDefinition.setText(currQuestion.getDefinition());
        tvQuizNumber.setText(String.valueOf(quizNumber));


        // Prepare and display images

        displayImages();
        setImagesClickable(true);


    }

    /**
     * Set shared preferences. Gets the quiz number
     * and counters from the shared preferences if available
     * or the default.
     */


    /**
     * Save values to state
     *
     * @param {Bundle} savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        Log.d(TAG, "onSaveInstanceState()");

        savedInstanceState.putInt("quizNumber", quizNumber);
        savedInstanceState.putInt("position", position);
        savedInstanceState.putBoolean("answeredCorrectly", answeredCorrectly);
        savedInstanceState.putInt("currQuestionIndex", questions.indexOf(currQuestion));

        /*
        if(level.equals("easy")) {
            savedInstanceState.putInt("currQuestionIndex", questions_easy.indexOf(currQuestion));
        }else{//if(level.equals("hard"))
            savedInstanceState.putInt("currQuestionIndex", questions_hard.indexOf(currQuestion));
        }
        */



    }

    /**
     * Save quiz number and correct
     * counter in saved preferences
     */

    /**
     * Set all four images to either clickable
     * or not clickable
     *
     * @param clickable true if clickable
     */
    public void setImagesClickable(boolean clickable) {
        image1.setClickable(clickable);
        image2.setClickable(clickable);
        image3.setClickable(clickable);
        image4.setClickable(clickable);
    }



}
