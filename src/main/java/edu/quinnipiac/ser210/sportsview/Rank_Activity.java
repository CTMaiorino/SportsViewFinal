package edu.quinnipiac.ser210.sportsview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Rank_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_);

        String wins = BackEndData.getTotalWins();
        Double winsNum = Double.valueOf(wins);

        String loss = BackEndData.getTotalLoss();
        Double lossNum = Double.valueOf(loss);

        double efficientStat = winsNum/lossNum;

        TextView efficientTxt = findViewById(R.id.slot_2);
        efficientTxt.setText(String.valueOf(efficientStat));

        int rank = (int) Math.ceil(efficientStat);
        TextView rankTxt = findViewById(R.id.slot_4);
        rankTxt.setText(String.valueOf(rank));

        configureReturnButton();
    }


    private void configureReturnButton(){
        Button playButton = (Button) findViewById(R.id.return_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rank_Activity.this, Team_Activity.class);
                startActivity(intent);
            }
        });
    }
}
