package edu.quinnipiac.ser210.sportsview;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String userInput = null;
    static String LOG_TAG = MainActivity.class.getSimpleName();
    private String url1 = "https://api-football-v1.p.rapidapi.com/statistics/1/";
    private static String url2 = "https://api-football-v1.p.rapidapi.com/players/2019/";
    private HashMap<String, String> teamHashMap = BackEndData.getTeamHashMap();
   // private HashMap<String, String> teamLogo = new HashMap<String, String>();
    private String[] goalsFor = new String[2];
    private String[] goalsAgainst = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = getIntent();
        //teamHashMap = (HashMap<String, String>) intent.getSerializableExtra("teamData");
        //teamLogo = (HashMap <String,String>) intent.getSerializableExtra("teamLogo");

        configureFavoritesButton();
        configureSearchButton();
    }

    // Method to set functionality to the Favorites button
    private void configureFavoritesButton() {
        Button playButton = (Button) findViewById(R.id.favorites_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Splash_Activity.class);
                startActivity(intent);
            }
        });
    }

    // Method to set functionality to the Search button. Allows the async class to run.
    private void configureSearchButton() {
        Button playButton = (Button) findViewById(R.id.search_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText userText = findViewById(R.id.searchBar);
                userInput = userText.getText().toString();
                BackEndData.setUserInput(userInput);
                FetchTeamData dataRun = new FetchTeamData();
                dataRun.execute(teamHashMap.get(userInput)); // Not using the param on test runs, will use to populate HashTable.
                new FetchTeamLineUp().execute(teamHashMap.get(userInput));
            }
        });
    }

    //Async Class
    private class FetchTeamData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String TeamData = null;


            // Connection to API. Currently Working :)
            try {
                URL url = new URL(url1 + params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-RapidAPI-Key", "aa4e75d260mshcddad1078f250e4p175b69jsn50acf7a60e26"); // API Key


                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();
                if (in == null) {
                    return "Bad inputStream connection";
                }

                reader = new BufferedReader(new InputStreamReader(in));
                // call getBufferString to get the string from the buffer.

                String TeamDataJsonString = getBufferStringFromBuffer(reader).toString();

                // call a method to parse the json data and return a string.
                TeamData = getTeamData(TeamDataJsonString);


            } catch (Exception e) {
                Log.e(LOG_TAG,"Error" + e.getMessage());
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG,"Error" + e.getMessage());
                        return null;
                    }


                }
            }
            return TeamData;
        }

        // Starts the Team_Activity with the results of the search.
        protected void onPostExecute(String result){
            if (result != null){
                Log.d(LOG_TAG, result);
                Intent intent = new Intent(MainActivity.this, Team_Activity.class);
                //intent.putExtra("teamData",teamHashMap);
               // intent.putExtra("teamFlag",teamLogo);
               // intent.putExtra("goals",goalsFor);
                //intent.putExtra("goalsAgainst",goalsAgainst);
               intent.putExtra("input", userInput);
                BackEndData.setTeamStats(goalsFor, goalsAgainst);
                startActivity(intent);
            }
        }


        private StringBuffer getBufferStringFromBuffer(BufferedReader br) throws Exception {
            StringBuffer buffer = new StringBuffer();
            String line;
            while((line = br.readLine()) != null){
                buffer.append(line + '\n');
            }

            if (buffer.length() == 0) {
                return null;
            }
            return buffer;
        }

        // Retrieving Specific Data from the JSON for testing.
        public String getTeamData(String teamDataJsonStr) throws JSONException {
            JSONObject teamDataJSONObj = new JSONObject(teamDataJsonStr);

            //Moving through the JSON Object to reach team information.
            JSONObject test = teamDataJSONObj.getJSONObject("api");
            JSONObject test3 = test.getJSONObject("stats");
            JSONObject test4 = test3.getJSONObject("goals");

            JSONObject test5 = test4.getJSONObject("goalsFor");
            for (int i = 0; i < 2; i ++){
                if (i == 0) {
                    goalsFor[i] = test5.getString("home");
                }
                if (i == 1) {
                    goalsFor[i] = test5.getString("away");
                }
            }
            JSONObject test6 = test4.getJSONObject("goalsAgainst");
            for (int i = 0; i < 2; i ++){
                if (i == 0) {
                    goalsAgainst[i] = test6.getString("home");
                }
                if (i == 1) {
                    goalsAgainst[i] = test6.getString("away");
                }
            }
            JSONObject test7 = test3.getJSONObject("matchs");
            JSONObject test8 = test7.getJSONObject("wins");
            BackEndData.setTotalWins(test8.getString("total"));
            JSONObject test9 = test7.getJSONObject("draws");
            BackEndData.setTotalDraw(test9.getString("total"));
            JSONObject test10 = test7.getJSONObject("loses");
            BackEndData.setTotalLoss(test10.getString("total"));

            //returns the team id and name
            return ("done");
        }
    }

    //Async Class
    private static class FetchTeamLineUp extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String TeamData;


            // Connection to API. Currently Working :)
            try {
                URL url = new URL(url2 + params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-RapidAPI-Key", "aa4e75d260mshcddad1078f250e4p175b69jsn50acf7a60e26"); // API Key


                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();
                if (in == null) {
                    return "Bad inputStream connection";
                }

                reader = new BufferedReader(new InputStreamReader(in));
                // call getBufferString to get the string from the buffer.

                String TeamDataJsonString = getBufferStringFromBuffer(reader).toString();

                // call a method to parse the json data and return a string.
                TeamData = getTeamData(TeamDataJsonString);


            } catch (Exception e) {
                Log.e(LOG_TAG, "Error" + e.getMessage());
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Error" + e.getMessage());
                        return null;
                    }


                }
            }
            return TeamData;
        }

        // Starts the Team_Activity with the results of the search.
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.d(LOG_TAG, result);
            }
        }


        private StringBuffer getBufferStringFromBuffer(BufferedReader br) throws Exception {
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line + '\n');
            }

            if (buffer.length() == 0) {
                return null;
            }
            return buffer;
        }

        // Retrieving Specific Data from the JSON for testing.
        public String getTeamData(String teamDataJsonStr) throws JSONException {
            JSONObject teamDataJSONObj = new JSONObject(teamDataJsonStr);

            //Moving through the JSON Object to reach team information.
            JSONObject test = teamDataJSONObj.getJSONObject("api");
            JSONArray test2 = test.getJSONArray("players");

            for (int i = 0; i <= 7; i++) {
            JSONObject endPoint = test2.getJSONObject(i);
            BackEndData.setText(endPoint.getString("player"), i);
            }
            //returns the team id and name
            return ("done");
        }
    }

}
