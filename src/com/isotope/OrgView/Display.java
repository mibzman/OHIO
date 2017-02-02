package com.isotope.OrgView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Sam on 11/14/2015.
 */
public class Display extends Activity{

    SharedPreferences OrgView_data;
    public static String filename1 = "first";
    public static String filename2 = "second";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        Firebase.setAndroidContext(this);
        OrgView_data = getSharedPreferences(filename2, 0);
        SharedPreferences.Editor editor = OrgView_data.edit();
        int place = OrgView_data.getInt("index", 0);
        String key = Integer.toString(place +1);

        editor.commit();

        Intent intent20 = getIntent();
        final String input = intent20.getStringExtra("barcodeResult");
        editor.putString(key, input);
        editor.putInt("index", place + 1);
        Intent openDisplay = getIntent();
        boolean isvanGogh = openDisplay.getBooleanExtra("isvanGogh", false);
        if(isvanGogh) {
            int recievedNumber2 = 1;
            final int recievedNumber = recievedNumber2;
            boolean isOrgTag = intent20.getBooleanExtra("orgTag", false);
            if(isOrgTag){
                if(recievedNumber == 10000000){
                    openHomePage(true);
                }else{
                    openHomePage(false);
                }
            }
            final TextView textView = (TextView) findViewById(R.id.textView);
            final TextView titleView = (TextView) findViewById(R.id.titleText);
            // Get a reference to our posts
            Firebase ref = new Firebase("https://admin1.firebaseio.com/uakron");
// Attach an listener to read the data at our posts reference
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                    int counter = 1;
                    for (DataSnapshot doodleSnapshot : snapshot.getChildren()) {
                        Blogpost doodle = doodleSnapshot.getValue(Blogpost.class);
                        titleView.setText(doodle.getAuthor());
                        textView.setText(doodle.getTitle());

                        if(counter == recievedNumber){
                            saveEntry(input, doodle.getTitle());
                            break;
                        }else{
                            counter++;
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });

            //textView.setText(" " + input + " ");
            //textView.setText("");
            textView.setMovementMethod(new ScrollingMovementMethod());

            Button upFont = (Button) findViewById(R.id .upFont);
            upFont.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize() + 20 );
                }
            });
            Button downFont = (Button) findViewById(R.id.downFont);
            downFont.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize() - 20 );
                }
            });

            Button scan = (Button) findViewById(R.id.scan);
            scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goScan();
                }
            });
        }else{
            int recievedNumber2 = Integer.parseInt(input);
            final int recievedNumber = recievedNumber2;
            boolean isOrgTag = intent20.getBooleanExtra("orgTag", false);
            if(isOrgTag){
                if(recievedNumber == 10000000){
                    openHomePage(true);
                }else{
                    openHomePage(false);
                }
            }
            final TextView textView = (TextView) findViewById(R.id.textView);
            final TextView titleView = (TextView) findViewById(R.id.titleText);
            // Get a reference to our posts
            Firebase ref = new Firebase("https://admin1.firebaseio.com/uakron");
// Attach an listener to read the data at our posts reference
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                    int counter = 1;
                    for (DataSnapshot doodleSnapshot : snapshot.getChildren()) {
                        Blogpost doodle = doodleSnapshot.getValue(Blogpost.class);
                        titleView.setText(doodle.getAuthor());
                        textView.setText(doodle.getTitle());

                        if(counter == recievedNumber){
                            saveEntry(input, doodle.getTitle());
                            break;
                        }else{
                            counter++;
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });

            //textView.setText(" " + input + " ");
            //textView.setText("");
            textView.setMovementMethod(new ScrollingMovementMethod());

            Button upFont = (Button) findViewById(R.id.upFont);
            upFont.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize() + 20 );
                }
            });
            Button downFont = (Button) findViewById(R.id.downFont);
            downFont.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize() - 20 );
                }
            });

            Button scan = (Button) findViewById(R.id.scan);
            scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goScan();
                }
            });
        }

    }

    public void openHomePage(boolean input){
        if(input){
            Intent homePageIntent = new Intent(this, HomePage.class);
            homePageIntent.putExtra("isAkron", true);
            startActivity(homePageIntent);
        }else{
            Intent homePageIntent = new Intent(this, HomePage.class);
            homePageIntent.putExtra("isAkron", false);
            startActivity(homePageIntent);
        }
    }

    public void goScan(){
        Intent goScan = new Intent(this, MyActivity.class);
        startActivity(goScan);
    }

    public void saveEntry(String key, String title){
        OrgView_data = getSharedPreferences(filename1, 0);
        SharedPreferences.Editor editor = OrgView_data.edit();
        editor.putString(key, title);
        editor.commit();
    }
}
