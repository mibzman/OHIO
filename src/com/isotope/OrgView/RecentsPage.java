package com.isotope.OrgView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.*;

/**
 * Created by Sam on 11/14/2015.
 */
public class RecentsPage extends Activity {
    ListView listView ;
    SharedPreferences OrgView_data;
    public static String filename1 = "first";
    public static String filename2 = "second";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recents);
        OrgView_data = getSharedPreferences(filename2, 0);
        int index = OrgView_data.getInt("index", 0);
        int counter;

        Button homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

        Button scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goScan();
            }
        });
        /*
        String[] values = new String[1000];
        for(counter = 0; counter < index; counter++){
            String thisKey = Integer.toString(counter);
            values[counter] = getTitle(OrgView_data.getString(thisKey, "butt"));
        }
*/
        String[] values = new String[] { "Van Gogh",
                "Picasso",
                "da Vinci",
                "Dali",
                "da Vinci",
                "van Gogh",
        };
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Defined Array values to show in ListView


        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);


                        openDisplay();
                }

                // Show Alert





        });
    }
    public String getTitle(String input){
        OrgView_data = getSharedPreferences(filename1, 0);
        return OrgView_data.getString(input, "butyt");
    }

    public void openDisplay(){
        Intent openDisplay = new Intent(this, Display.class);
        openDisplay.putExtra("isvanGogh", true);
        startActivity(openDisplay);
    }

    public void goHome(){
        Intent thisisanintent= new Intent(this, HomePage.class);
        startActivity(thisisanintent);
    }

    public void goScan(){
        Intent thisisanintent= new Intent(this, MyActivity.class);
        startActivity(thisisanintent);
    }

}
