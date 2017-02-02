package com.isotope.OrgView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Sam on 11/14/2015.
 */
public class StartScreen extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);
        Button button = (Button) findViewById(R.id.startbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNow();
            }
        });
    }

    public void goNow(){
        Intent startIntent = new Intent(this, MyActivity.class);
        startIntent.putExtra("start", true);
        startActivity(startIntent);
    }

    @Override
    public void onStop(){
        super.onStop();

    }
}
