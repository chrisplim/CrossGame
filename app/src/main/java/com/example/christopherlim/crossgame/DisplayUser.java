package com.example.christopherlim.crossgame;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class DisplayUser extends Activity {

    private UserDBHelper mydb ;
    TextView lastname ;
    TextView firstname ;
    TextView age ;
    TextView gender ;
    TextView orientation ;
    TextView phonenumber ;
    TextView tagline ;
    String deviceid;
    String name;
    //RadioGroup radioSexGroup;
   //RadioButton radioSexButton;
   //RadioGroup radioOrientationGroup;
    //RadioButton radioOrientationButton;

    int id_To_Update = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);
        lastname = (TextView) findViewById(R.id.editTextLastName2);
        firstname = (TextView) findViewById(R.id.editTextFirstName2);
        age = (TextView) findViewById(R.id.editTextAge2);
        gender = (TextView) findViewById(R.id.editTextGender2);
        orientation = (TextView) findViewById(R.id.editTextOrientation2);
        //radioSexGroup= (RadioGroup) findViewById(R.id.radioSex);
        //radioOrientationGroup= (RadioGroup) findViewById(R.id.radioOrientation);
        phonenumber = (TextView) findViewById(R.id.editTextPhoneNumber2);
        tagline = (TextView) findViewById(R.id.editTextTagLine2);

        mydb = new UserDBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();   //do we need this?
                //change this to retrieve from RDS server
                //use these strings for RDS lookup
                deviceid = rs.getString(rs.getColumnIndex(UserDBHelper.USERS_COLUMN_DEVICEID));
                name = rs.getString(rs.getColumnIndex(UserDBHelper.USERS_COLUMN_NAME));
                //change below to update from RDS
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
                //change
               /*radioSexGroup.setFocusable(false);
                radioSexGroup.setClickable(false);

                radioOrientationGroup.setFocusable(false);
                radioOrientationGroup.setClickable(false);*/

                gender.setText((CharSequence) gende);
                gender.setFocusable(false);
                gender.setClickable(false);

                orientation.setText((CharSequence) orientatio);
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

        //addListenerOnButton_g();
       // addListenerOnButton_o();
    }

   /* public void addListenerOnButton_g() {

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
    } */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_user, menu);
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
            /*case R.id.Edit_Contact:
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

                return true; */
            default:
                return super.onOptionsItemSelected(item);

        }
    }
/*
    public void run(View view)
    {
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateContact(id_To_Update, lastname.getText().toString(), firstname.getText().toString(), age.getText().toString(), gender, orientation, phonenumber.getText().toString(), tagline.getText().toString())){

                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                if(mydb.insertContact(lastname.getText().toString(), firstname.getText().toString(), age.getText().toString(), gender, orientation, phonenumber.getText().toString(), tagline.getText().toString())){
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
    }*/
}
