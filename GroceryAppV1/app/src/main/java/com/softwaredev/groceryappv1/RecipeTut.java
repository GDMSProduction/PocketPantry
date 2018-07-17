package com.softwaredev.groceryappv1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import org.w3c.dom.Text;

public class RecipeTut extends AppCompatActivity {
    int Step = 1;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_tut);
        final TextView textView = findViewById(R.id.insTextView);
        textView.setText("Remember to make sure you're prepared!");

        Context context = this;

        VideoView videoView = findViewById(R.id.tutVideo);
        String path="https://www.demonuts.com/Demonuts/smallvideo.mp4";
        Uri uri= Uri.parse(path);
        videoView.setVideoURI(uri);
        videoView.start();

        final Button button = findViewById(R.id.nextStepButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(RecipeList.GetName() == "Grilled Cheese") {
                    if (Step == 1) {
                        textView.setText("Ingredients required: \nBlocked white cheddar cheese\nSliced munster cheese\nSliced bread of choice\nButter or butter substitute of choice\n\n" +
                                "Utensils required: \nSkillet\nHeat resistant flipper\nSpreading knife\nCheese slicer(Wire or otherwise)\nBasic kitchen knife");
                        Step++;
                    }
                    else if (Step == 2) {
                        textView.setText("Use your cheese slicer to cut generous slices off your block of white cheddar\nSet these to the side for now, grab two slices of bread and butter one side generously on each\nSet your skillet over low-medium heat and move onto the next step where a timer will start!");
                        Step++;
                    } else if (Step == 3) {
                        final TextView textView = findViewById(R.id.insTextView);
                        new CountDownTimer(15000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                if (millisUntilFinished >= 60000 && millisUntilFinished <= 90000)
                                    textView.setText("Time remaining: 1:" + millisUntilFinished / 1000 % 60);
                                else if(millisUntilFinished <= 60000)
                                {
                                    textView.setText("Time remaining: " + millisUntilFinished / 1000);
                                }
                                findViewById(R.id.nextStepButton).setVisibility(View.INVISIBLE);
                            }
                            public void onFinish() {
                                sendNotification(v);
                                findViewById(R.id.nextStepButton).setVisibility(View.VISIBLE);
                                textView.setText("Now place the bread butter side down into the skillet placing 2 slices of cheddar on one piece of bread and 2 slices of munster on the other\nwait until you can see the cheese start to glisten and melt. Use your flipper to place the two pieces of bread together\nContinue cooking each side evenly flipping the sandwich frequently until golden brown\n Slice and enjoy!");
                                Step++;
                            }
                        }.start();
                    } else if (Step == 4) {
                        textView.setText("Congratulations, you actually clicked through this mess of an example.");
                    } else {
                        textView.setText("WTF");
                    }
                }
                else if(RecipeList.GetName() == "Lemon Pepper Chicken") {
                    if(Step == 1) {
                        textView.setText("Ingredients required: 1/2 c. all-purpose flour\n1 tbsp. lemon pepper seasoning\n1 tsp. kosher salt\n2 lemons, divided\n1 lb. Boneless Skinless Chicken Breast\n2 tbsp. extra-virgin olive oil\n1/2 c. low-sodium chicken broth\n2 tbsp. butter\n2 cloves garlic\nChopped parsley, for garnish\n\nUtensils required: \nMedium sized bowl\nLarge skillet\nTongs\nLadle");
                        Step ++;
                    }
                    else if (Step == 2)
                    {
                        textView.setText("Preheat your oven to 400 degrees now, we will need it in about 10 minutes");
                        Step ++;
                    }
                    else if (Step == 3)
                    {
                        textView.setText("In your bowl, combine flour, lemon pepper, salt, and zest of one lemon.\n Toss chicken breasts in the flour mixture until fully coated.\n Slice 1 lemon into thin rounds.");
                        Step ++;
                    }
                    else if (Step == 4)
                    {
                        textView.setText("Now put the skillet over medium high heat\nAdd olive oil and lay the chicken breasts and cook until golden brown, this will take around 5 minutes.");
                        Step ++;
                    }
                    else if (Step == 5)
                    {

                        new CountDownTimer(300000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                findViewById(R.id.nextStepButton).setVisibility(View.INVISIBLE);
                                if (millisUntilFinished >= 240000 && millisUntilFinished <= 300000)
                                    textView.setText("Time remaining: 4:" + millisUntilFinished / 1000 % 60);
                               else if (millisUntilFinished > 180000 && millisUntilFinished < 240000)
                                {
                                    textView.setText("Time remaining: 3:" + millisUntilFinished / 1000 % 60);
                                }
                                else if (millisUntilFinished > 120000 && millisUntilFinished < 180000)
                                {
                                    textView.setText("Time remaining: 2:" + millisUntilFinished / 1000 % 60);
                                }
                                else if (millisUntilFinished > 60000 && millisUntilFinished < 120000)
                                {
                                    textView.setText("Time remaining: 1:" + millisUntilFinished / 1000 % 60);
                                }
                                else if (millisUntilFinished > 0 && millisUntilFinished < 60000)
                                {
                                    textView.setText("Time remaining: " + millisUntilFinished / 1000 % 60);
                                }
                            }

                            public void onFinish() {
                                findViewById(R.id.nextStepButton).setVisibility(View.VISIBLE);
                                textView.setText("Now flip the chicken breast!");
                                Step++;
                                sendNotification(v);
                            }
                        }.start();
                    }
                    else if (Step == 6)
                    {

                        new CountDownTimer(300000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                findViewById(R.id.nextStepButton).setVisibility(View.INVISIBLE);
                                if (millisUntilFinished >= 240000 && millisUntilFinished <= 300000)
                                    textView.setText("Time remaining: 4:" + millisUntilFinished / 1000 % 60);
                                else if (millisUntilFinished > 180000 && millisUntilFinished < 240000)
                                {
                                    textView.setText("Time remaining: 3:" + millisUntilFinished / 1000 % 60);
                                }
                                else if (millisUntilFinished > 120000 && millisUntilFinished < 180000)
                                {
                                    textView.setText("Time remaining: 2:" + millisUntilFinished / 1000 % 60);
                                }
                                else if (millisUntilFinished > 60000 && millisUntilFinished < 120000)
                                {
                                    textView.setText("Time remaining: 1:" + millisUntilFinished / 1000 % 60);
                                }
                                else if (millisUntilFinished > 0 && millisUntilFinished < 60000)
                                {
                                    textView.setText("Time remaining: " + millisUntilFinished / 1000 % 60);
                                }
                            }

                            public void onFinish() {
                                findViewById(R.id.nextStepButton).setVisibility(View.VISIBLE);
                                textView.setText("Now remove the skillet from the heat and turn the burner off.");
                                Step++;
                                sendNotification(v);
                            }
                        }.start();
                    }
                    else if (Step == 7)
                    {
                        textView.setText("To the same skillet, add the chicken broth, butter, garlic and lemon slices and transfer skillet to the oven.\n Bake until the chicken reaches an internal temperature of 170° F and the sauce has reduced slightly, around 13 minutes.");
                        Step++;
                    }
                    else if (Step == 8)
                    {
                        new CountDownTimer(780000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                findViewById(R.id.nextStepButton).setVisibility(View.INVISIBLE);
                                if(millisUntilFinished >= 720000 && millisUntilFinished <= 780000)
                                {
                                    textView.setText("Time remaining: 12:" + millisUntilFinished / 1000 % 60);
                                }
                                else if(millisUntilFinished >= 660000 && millisUntilFinished <= 720000)
                                {
                                    textView.setText("Time remaining: 11:" + millisUntilFinished / 1000 % 60);
                                }
                                else if(millisUntilFinished >= 600000 && millisUntilFinished <= 660000)
                                {
                                    textView.setText("Time remaining: 10:" + millisUntilFinished / 1000 % 60);
                                }
                                else if(millisUntilFinished >= 540000 && millisUntilFinished <= 600000)
                                {
                                    textView.setText("Time remaining: 9:" + millisUntilFinished / 1000 % 60);
                                }
                                else if(millisUntilFinished >= 480000 && millisUntilFinished <= 540000)
                                {
                                    textView.setText("Time remaining: 8:" + millisUntilFinished / 1000 % 60);
                                }
                                else if(millisUntilFinished >= 420000 && millisUntilFinished <= 480000)
                                {
                                    textView.setText("Time remaining: 7:" + millisUntilFinished / 1000 % 60);
                                }
                                else if(millisUntilFinished >= 360000 && millisUntilFinished <= 420000)
                                {
                                    textView.setText("Time remaining: 6:" + millisUntilFinished / 1000 % 60);
                                }
                                else if(millisUntilFinished >= 300000 && millisUntilFinished <= 360000)
                                {
                                    textView.setText("Time remaining: 5:" + millisUntilFinished / 1000 % 60);
                                }
                                else if (millisUntilFinished >= 240000 && millisUntilFinished <= 300000)
                                    textView.setText("Time remaining: 4:" + millisUntilFinished / 1000 % 60);
                                else if (millisUntilFinished > 180000 && millisUntilFinished < 240000)
                                {
                                    textView.setText("Time remaining: 3:" + millisUntilFinished / 1000 % 60);
                                }
                                else if (millisUntilFinished > 120000 && millisUntilFinished < 180000)
                                {
                                    textView.setText("Time remaining: 2:" + millisUntilFinished / 1000 % 60);
                                }
                                else if (millisUntilFinished > 60000 && millisUntilFinished < 120000)
                                {
                                    textView.setText("Time remaining: 1:" + millisUntilFinished / 1000 % 60);
                                }
                                else if (millisUntilFinished > 0 && millisUntilFinished < 60000)
                                {
                                    textView.setText("Time remaining: " + millisUntilFinished / 1000 % 60);
                                }
                            }

                            public void onFinish() {
                                findViewById(R.id.nextStepButton).setVisibility(View.VISIBLE);
                                textView.setText("Now plate and ladle the sauce on top of the chicken, then garnish with parsley");
                                Step++;
                                sendNotification(v);
                            }
                        }.start();
                    }
                    else if(Step == 9)
                    {
                        textView.setText("Press the back button to return to the recipe book.");
                    }
                }
            }
        });
    }
    public void sendNotification(View view) {
        CharSequence name = "my_channel2";
        String CHANNEL_ID = "my_channel_02";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);

        Intent intent = new Intent(this, RecipeTut.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);
        mBuilder.setSmallIcon(R.drawable.shitsgoingdown);
        mBuilder.setContentTitle("Time for the next step!");
        mBuilder.setContentText("Your recipe timer has finished!");

        notificationManager.notify((int)(System.currentTimeMillis()/1000), mBuilder.build());
    }
}

