package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        findViewById(R.id.close_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.save_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent data = new Intent();
                data.putExtra("questionTextField",
                        ((EditText) findViewById(R.id.question_textField)).getText().toString());
                data.putExtra("answerTextField",
                        ((EditText) findViewById(R.id.answer_textField)).getText().toString());
                setResult(RESULT_OK, data);

                finish();
            }
        });
    }
}
