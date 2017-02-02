package com.isotope.OrgView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.firebase.client.Firebase;

/**
 * Created by Sam on 11/14/2015.
 */
public class HomePage extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        Firebase.setAndroidContext(this);

        Intent homePageIntent = getIntent();
        final boolean isAkron = homePageIntent.getBooleanExtra("isAkron", true);
        Button button = (Button) findViewById(R.id.goButton);

        TextView title = (TextView) findViewById(R.id.title);
        TextView textbody = (TextView) findViewById(R.id.textbody);

        if(isAkron){
            title.setText("The Akron Museam of Art");
            textbody.setText("Welcome to the The Akron Museam of Art!  Here you will see all kinds of wonders that are so fantastic you will want to spend hours looking at our stuff!  Enjoy scanning things!");
            button.setText("Start Scanning!");
        }else{
            title.setText("An error occured.  Please scan the correct QR code.");
            textbody.setText(" ");
            button.setText("Try Again");
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScanning(isAkron);
            }
        });
    }

    public void startScanning(boolean input){
        Intent startScan = new Intent(this, MyActivity.class);
        startScan.putExtra("again", !input);
        startActivity(startScan);
    }
}
