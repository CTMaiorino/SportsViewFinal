package edu.quinnipiac.ser210.sportsview;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;

import android.widget.TextView;

public class LineUp_Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_up__detail);


        TextView slot_1 = findViewById(R.id.slot_1);
        slot_1.setText(BackEndData.getText(0));

        TextView slot_2 = findViewById(R.id.slot_2);
        slot_2.setText(BackEndData.getText(1));

        TextView slot_3 = findViewById(R.id.slot_3);
        slot_3.setText(BackEndData.getText(2));

        TextView slot_4 = findViewById(R.id.slot_4);
        slot_4.setText(BackEndData.getText(3));

        TextView slot_5 = findViewById(R.id.slot_5);
        slot_5.setText(BackEndData.getText(4));

        TextView slot_6 = findViewById(R.id.slot_6);
        slot_6.setText(BackEndData.getText(5));

        TextView slot_7 = findViewById(R.id.slot_7);
        slot_7.setText(BackEndData.getText(6));

        TextView slot_8 = findViewById(R.id.slot_8);
        slot_8.setText(BackEndData.getText(7));

        //System.out.println(BackEndData.getTextOne());

       configureReturnButton();
    }


    private void configureReturnButton(){
        Button playButton = (Button) findViewById(R.id.return_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LineUp_Detail.this, Team_Activity.class);
                startActivity(intent);
            }
        });
    }



}