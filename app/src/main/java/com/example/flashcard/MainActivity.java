package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.add_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.
                        get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.
                        get(currentCardDisplayedIndex).getAnswer());

                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                String newQuestion = data.getExtras().getString("questionTextField");
                String newAnswer = data.getExtras().getString("answerTextField");

                TextView currentFlashcardQuestion = findViewById(R.id.flashcard_question);
                currentFlashcardQuestion.setText(newQuestion);

                TextView currentFlashcardAnswer = findViewById(R.id.flashcard_answer);
                currentFlashcardAnswer.setText(newAnswer);
                currentFlashcardQuestion.setText(newQuestion);

                flashcardDatabase.insertCard(new Flashcard(newQuestion, newAnswer));
                allFlashcards = flashcardDatabase.getAllCards();

            }
        }
    }
}
