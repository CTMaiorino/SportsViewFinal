
package edu.quinnipiac.ser210.sportsview;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class Splash_Activity extends AppCompatActivity {

    String LOG_TAG = Splash_Activity.class.getSimpleName();
    private String url1 = "https://api-football-v1.p.rapidapi.com/teams/league/1";
    public HashMap<String, String> teamInfo = new HashMap<String, String>();
    public HashMap<String, String> teamImage = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        configureEnterButton();
    }


    private void configureEnterButton(){
        Button playButton = (Button) findViewById(R.id.enter_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchTeamData dataRun = new FetchTeamData();
                dataRun.execute(); // Not using the param on test runs, will use to populate HashTable.
                //Intent intent = new Intent(Splash_Activity.this, MainActivity.class);
                //startActivity(intent);
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
                URL url = new URL(url1);

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
            if (result == "HashMap Built"){
                Log.d(LOG_TAG, result);
                Intent intent = new Intent(Splash_Activity.this, MainActivity.class);
               // intent.putExtra("teamData",teamInfo);
               // intent.putExtra("teamLogo", teamImage);
                BackEndData.setTeamHashMap(teamInfo);
                BackEndData.setTeamLogo(teamImage);
                //BackEndData dataTransfer = new BackEndData(teamInfo,teamImage);
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
            JSONObject test2 = test.getJSONObject("teams");
            for (int i = 1; i <= 32; i ++) {
                JSONObject test3 = test2.getJSONObject(String.valueOf(i));
                teamInfo.put(test3.getString("name"), test3.getString("team_id"));
                teamImage.put(test3.getString("team_id"), test3.getString("logo"));
            }
            //returns the team id and name
            return ("HashMap Built");
        }
    }

}