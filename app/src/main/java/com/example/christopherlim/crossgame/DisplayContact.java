package com.example.christopherlim.crossgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DisplayContact extends Activity implements AdapterView.OnItemSelectedListener{

    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;
    TextView lastname ;
    TextView firstname ;
    TextView age ;
    TextView gender ;
    TextView orientation ;
    TextView phonenumber ;
    TextView tagline ;
    Spinner spinner;
    ArrayAdapter <String> dataAdapter;
    List <String> choices;



    int id_To_Update = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        lastname = (TextView) findViewById(R.id.editTextLastName);
        firstname = (TextView) findViewById(R.id.editTextFirstName);
        age = (TextView) findViewById(R.id.editTextAge);
        gender =  (TextView) findViewById(R.id.editTextGender);
        // Spinner element
        spinner = (Spinner) findViewById(R.id.gender_spinner);
        orientation = (TextView) findViewById(R.id.editTextOrientation);
        phonenumber = (TextView) findViewById(R.id.editTextPhoneNumber);
        tagline = (TextView) findViewById(R.id.editTextTagLine);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();
                String lastnam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_LASTNAME));
                String firstnam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_FIRSTNAME));
                String ag = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_AGE));
                String gende = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_GENDER));
                String orientatio = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_ORIENTATION));
                String phonenumbe = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONENUMBER));
                String taglin = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_TAGLINE));

                if (!rs.isClosed())
                {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                lastname.setText((CharSequence)lastnam);
                lastname.setFocusable(false);
                lastname.setClickable(false);

                firstname.setText((CharSequence)firstnam);
                firstname.setFocusable(false);
                firstname.setClickable(false);

                age.setText((CharSequence)ag);
                age.setFocusable(false);
                age.setClickable(false);

                gender.setText((CharSequence)gende);
                gender.setFocusable(false);
                gender.setClickable(false);

                spinner.setFocusable(false);
                spinner.setClickable(false);

                orientation.setText((CharSequence)orientatio);
                orientation.setFocusable(false);
                orientation.setClickable(false);

                phonenumber.setText((CharSequence) phonenumbe);
                phonenumber.setFocusable(false);
                phonenumber.setClickable(false);

                tagline.setText((CharSequence)taglin);
                tagline.setFocusable(false);
                tagline.setClickable(false);
            }
        }

        // Spinner element
       // Spinner spinner = (Spinner) findViewById(R.id.gender_spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        choices = new ArrayList < java.lang.String >();
        choices.add("male");
        choices.add("female");

        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, choices);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        if(item.equals("female") && gender.getText().toString().equals("male")){
            gender.setText("female");
        }
        if(item.equals("male") && !gender.getText().toString().equals("female")) {
            gender.setText("male");
        }
        //boolean animate = false;
        //spinner.setSelection(position, animate);
        //spinner.setAdapter(dataAdapter);
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
        //gender.setText(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_contact, menu);
            }
            else{
                getMenuInflater().inflate(R.menu.main, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.Edit_Contact:
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                lastname.setEnabled(true);
                lastname.setFocusableInTouchMode(true);
                lastname.setClickable(true);

                firstname.setEnabled(true);
                firstname.setFocusableInTouchMode(true);
                firstname.setClickable(true);

                age.setEnabled(true);
                age.setFocusableInTouchMode(true);
                age.setClickable(true);

                gender.setEnabled(false);
                gender.setFocusableInTouchMode(false);
                gender.setClickable(false);

                spinner.setEnabled(true);
                spinner.setFocusableInTouchMode(true);
                spinner.setClickable(true);

                orientation.setEnabled(true);
                orientation.setFocusableInTouchMode(true);
                orientation.setClickable(true);

                phonenumber.setEnabled(true);
                phonenumber.setFocusableInTouchMode(true);
                phonenumber.setClickable(true);

                tagline.setEnabled(true);
                tagline.setFocusableInTouchMode(true);
                tagline.setClickable(true);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteContact(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),com.example.christopherlim.crossgame.InfoInput.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view)
    {
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateContact(id_To_Update, lastname.getText().toString(), firstname.getText().toString(), age.getText().toString(), gender.getText().toString(), orientation.getText().toString(), phonenumber.getText().toString(), tagline.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                if(mydb.insertContact(lastname.getText().toString(), firstname.getText().toString(), age.getText().toString(), gender.getText().toString(), orientation.getText().toString(), phonenumber.getText().toString(), tagline.getText().toString())){
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                }
                //Intent intent = new Intent(getApplicationContext(),com.example.christopherlim.crossgame.InfoInput.class);
                Intent intent = new Intent(getApplicationContext(),com.example.christopherlim.crossgame.StartConnect.class);
                startActivity(intent);
            }
        }
    }
}