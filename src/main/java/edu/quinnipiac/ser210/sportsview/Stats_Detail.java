package edu.quinnipiac.ser210.sportsview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Stats_Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats__detail);

        TextView slot_2 = findViewById(R.id.slot_2);
        slot_2.setText(BackEndData.getTotalWins());

        TextView slot_4 = findViewById(R.id.slot_4);
        slot_4.setText(BackEndData.getTotalLoss());

        TextView slot_6 = findViewById(R.id.slot_6);
        slot_6.setText(BackEndData.getTotalDraw());

        configureReturnButton();
    }


    private void configureReturnButton(){
        Button playButton = (Button) findViewById(R.id.return_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Stats_Detail.this, Team_Activity.class);
                startActivity(intent);
            }
        });
    }
}
