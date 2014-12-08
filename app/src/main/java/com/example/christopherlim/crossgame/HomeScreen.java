package com.example.christopherlim.crossgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class HomeScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        //make UI to start connection here?
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks the Send button */
    public void startConnect2(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, wifiP2PInit.class);
        startActivity(intent);
    }

    public void startConnectedList2(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ConnectedList.class);
        startActivity(intent);
    }

    public void startInput(View view) { //changed from infoinput
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("id", 0);
        Intent intent = new Intent(getApplicationContext(),com.example.christopherlim.crossgame.DisplayContact.class);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }
}
