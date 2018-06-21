package com.softwaredev.groceryappv1;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecipeTut extends AppCompatActivity {
    int Step = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_tut);
        final Button button = findViewById(R.id.nextStepButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Step == 2)
                {
                    TextView textView = findViewById(R.id.insTextView);
                    textView.setText("Use your cheese slicer to cut generous slices off your block of white cheddar\nSet these to the side for now, grab two slices of bread and butter one side generously on each\nSet your skillet over low-medium heat and move onto the next step where a timer will start!");
                    Step ++;
                }
                else if(Step == 3)
                {
                    final TextView textView = findViewById(R.id.insTextView);
                    new CountDownTimer(5000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            textView.setText("seconds remaining: " + millisUntilFinished / 1000);
                        }

                        public void onFinish() {
                            textView.setText("Now place the bread butter side down into the skillet placing 2 slices of cheddar on one piece of bread and 2 slices of munster on the other\nwait until you can see the cheese start to glisten and melt. Use your flipper to place the two pieces of bread together\nContinue cooking each side evenly flipping the sandwich frequently until golden brown\n Slice and enjoy!");
                            Step++;
                        }
                    }.start();
                }
                else if(Step == 4)
                {
                    TextView textView = findViewById(R.id.insTextView);
                    textView.setText("Congratulations, you actually clicked through this mess of an example.");
                }
                else
                {
                    Step = 4;
                }
            }
        });
    }
}
