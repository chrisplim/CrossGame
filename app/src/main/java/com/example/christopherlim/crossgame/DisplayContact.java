package com.example.christopherlim.crossgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class DisplayContact extends Activity{

    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;

    TextView lastname ;
    TextView firstname ;
    private RDSHelper myRDS;

    TextView age ;
    String gender ;
    String orientation ;
    TextView phonenumber ;
    TextView tagline ;
    RadioGroup radioSexGroup;
    RadioButton radioSexButton;
    RadioGroup radioOrientationGroup;
    RadioButton radioOrientationButton;
    String android_id;

    int id_To_Update = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        lastname = (TextView) findViewById(R.id.editTextLastName);
        firstname = (TextView) findViewById(R.id.editTextFirstName);
        age = (TextView) findViewById(R.id.editTextAge);
        radioSexGroup= (RadioGroup) findViewById(R.id.radioSex);
        radioOrientationGroup= (RadioGroup) findViewById(R.id.radioOrientation);
        phonenumber = (TextView) findViewById(R.id.editTextPhoneNumber);
        tagline = (TextView) findViewById(R.id.editTextTagLine);
        android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        mydb = new DBHelper(this);
        myRDS = new RDSHelper(this, null, null, null, null, null, null, null, null, 1);
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value, 0);
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

                radioSexGroup.setFocusable(false);
                radioSexGroup.setClickable(false);

                radioOrientationGroup.setFocusable(false);
                radioOrientationGroup.setClickable(false);

                phonenumber.setText((CharSequence) phonenumbe);
                phonenumber.setFocusable(false);
                phonenumber.setClickable(false);

                tagline.setText((CharSequence)taglin);
                tagline.setFocusable(false);
                tagline.setClickable(false);
            }
        }

        addListenerOnButton_g();
        addListenerOnButton_o();
    }

    public void addListenerOnButton_g() {

        //radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(selectedId);
        gender = (String) radioSexButton.getText();
        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                radioSexButton = (RadioButton) findViewById(checkedId);
                gender = (String) radioSexButton.getText();
            }
        });
    }

    public void addListenerOnButton_o() {

        //radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        int selectedId = radioOrientationGroup.getCheckedRadioButtonId();
        radioOrientationButton = (RadioButton) findViewById(selectedId);
        orientation = (String) radioOrientationButton.getText();
        radioOrientationGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                radioOrientationButton = (RadioButton) findViewById(checkedId);
                orientation = (String) radioOrientationButton.getText();
            }
        });
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

                radioSexGroup.setEnabled(true);
                radioSexGroup.setFocusableInTouchMode(true);
                radioSexGroup.setClickable(false);

                radioOrientationGroup.setEnabled(true);
                radioOrientationGroup.setFocusableInTouchMode(true);
                radioOrientationGroup.setClickable(false);

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
                                mydb.deleteContact(id_To_Update, 0);
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
            String tempLastName = lastname.getText().toString();
            String tempFirstName = firstname.getText().toString();
            String tempAge = age.getText().toString();
            String tempGender = gender;
            String tempOrientation = orientation;
            String tempPhoneNumber = phonenumber.getText().toString();
            String tempTagLine = tagline.getText().toString();


            if(Value>0){
                String tempOldLastName = mydb.getLastName();
                String tempOldFirstName = mydb.getFirstName();
                String tempOldAge = mydb.getAge();
                String tempOldGender = mydb.getGender();
                String tempOldOrientation = mydb.getOrientation();
                String tempOldPhoneNumber = mydb.getPhoneNumber();
                String tempOldTagLine = mydb.getTagLine();


                //if (mydb.updateContact(id_To_Update, tempLastName, tempFirstName, tempAge, gender, orientation, tempPhoneNumber, tempTagLine)){

                    //Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_SHORT).show();

                    //Intent intent = new Intent(getApplicationContext(),com.example.christopherlim.crossgame.StartConnect.class);
                    //startActivity(intent);

                if(mydb.updateContact(id_To_Update, lastname.getText().toString(), firstname.getText().toString(), age.getText().toString(), gender, orientation, phonenumber.getText().toString(), tagline.getText().toString(), android_id, 0)){
                    Toast.makeText(getApplicationContext(), "User Successfully updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "User Update Failed", Toast.LENGTH_SHORT).show();
                    Log.d("updating user: ", myRDS.getErrorMessage());

                }


            }
            else{

                //new RDSHelper(this).execute(tempLastName, tempFirstName, tempAge, gender, orientation, tempPhoneNumber, tempTagLine);
                myRDS.execute(tempLastName, tempFirstName, tempAge, gender, orientation, tempPhoneNumber, tempTagLine, android_id);

                if(mydb.insertContact(tempLastName, tempFirstName, tempAge, gender, orientation, tempPhoneNumber, tempTagLine, android_id, 0)){
                    Log.d("Display Contact: ", "successfully inserted user");
                    Toast.makeText(getApplicationContext(), "User Inserted", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("inserting user","failed to insert user");
                    Toast.makeText(getApplicationContext(), "User Insert Failed", Toast.LENGTH_LONG).show();
                }

                //Intent intent = new Intent(getApplicationContext(),com.example.christopherlim.crossgame.InfoInput.class);
                Intent intent = new Intent(getApplicationContext(),com.example.christopherlim.crossgame.StartConnect.class);
                startActivity(intent);
            }
        }
    }

}