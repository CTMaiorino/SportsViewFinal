package edu.quinnipiac.ser210.sportsview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class Team_Activity extends AppCompatActivity {



    // Create a BackEnd Class to avoid passing back end data between classes
    // Use instance of BackEnd class to retrieve HashMap/Arrays
    //
    private HashMap<String, String> teamHashMap = new HashMap<String, String>();
    private HashMap<String, String> teamLogo = new HashMap<String, String>();
    private String[] goalsFor = new String[2];
    private String[] goalsAgainst = new String[2];
    ImageView flag;
    String userInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_);

        // Currently passing team data through hasMaps and arrays using Intents.
        // Will update code to use use an instance of a backEnd class instead.

        teamHashMap = BackEndData.getTeamHashMap();
        teamLogo = BackEndData.getTeamLogo();
        goalsFor = BackEndData.getGoalsFor();
        goalsAgainst = BackEndData.getGoalsAgainst();
        userInput = BackEndData.getUserInput();

        // Setting the text for each textView with information pulled off the API
        TextView teamName = (TextView) findViewById(R.id.teamName);
        teamName.setText(userInput);

        TextView forHomeGoals = findViewById(R.id.homeGoalsForValue);
        forHomeGoals.setText("Home: " + goalsFor[0]);

        TextView forAwayGoals = findViewById(R.id.awayGoalsForValue);
        forAwayGoals.setText("Away: " + goalsFor[1]);

        TextView AgstHomeGoals = findViewById(R.id.homeGoalsAgstValue);
        AgstHomeGoals.setText("Home: " + goalsAgainst[0]);

        TextView AgstAwayGoals = findViewById(R.id.awayGoalsAgstValue);
        AgstAwayGoals.setText("Away: " + goalsAgainst[1]);

        // Creating an instance of an Async taks to download each teams Flag
        flag = (ImageView) findViewById(R.id.teamFlag);
        new DownloadImage().execute(teamLogo.get(teamHashMap.get(userInput)));


        configureLineUpButton();
        configureReturnButton();
        configureStatsButton();
        configureRankButton();
    }

    //
    private void configureLineUpButton() {
        ImageButton playButton = (ImageButton) findViewById(R.id.lineUp_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Team_Activity.this, LineUp_Detail.class);
                startActivity(intent);
            }
        });
    }

    private void configureRankButton() {
        ImageButton playButton = (ImageButton) findViewById(R.id.standings_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Team_Activity.this, Rank_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void configureStatsButton() {
        ImageButton playButton = (ImageButton) findViewById(R.id.stats_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Team_Activity.this, Stats_Detail.class);
                startActivity(intent);
            }
        });
    }

    private void configureReturnButton() {
        Button playButton = (Button) findViewById(R.id.return_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Team_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    // DownloadImage AsyncTask
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            flag.setImageBitmap(result);
        }
    }

}