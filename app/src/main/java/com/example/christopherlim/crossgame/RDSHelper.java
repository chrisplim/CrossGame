package com.example.christopherlim.crossgame;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by ChristopherL on 12/5/2014.
 */
public class RDSHelper extends AsyncTask<String,Void,String> {

//public class RDSHelper {
    private Context context;
    private String errorMessage;
    private String outputMessage;
    private boolean successOfRequest;

    TextView requestSucceeded;

    public RDSHelper(Context context) {
        this.context = context;
        outputMessage = "";
        errorMessage = "";
        successOfRequest = false;
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            String lastName = (String) arg0[0];
            String firstName = (String) arg0[1];
            String age = (String) arg0[2];
            String gender = (String) arg0[3];
            String lookingFor = (String) arg0[4];
            String phoneNumber = (String) arg0[5];
            String tagLine = (String) arg0[6];

            Log.d("RDS Helper: ", lastName);
            Log.d("RDS Helper: ", firstName);
            Log.d("RDS Helper: ", age);
            Log.d("RDS Helper: ", gender);
            Log.d("RDS Helper: ", lookingFor);
            Log.d("RDS Helper: ", phoneNumber);
            Log.d("RDS Helper: ", tagLine);
            String link = "http://54.148.130.198/insertUser.php?";
            link += "last_name=" + lastName;
            link += "&" + "first_name=" + firstName;
            link += "&" + "age=" + age;
            link += "&" + "gender=" + gender;
            link += "&" + "looking_for=" + lookingFor;
            link += "&" + "phone_number=" + phoneNumber;
            link += "&" + "tag_line=" + tagLine;

            Log.d("RDS Helper: ", link);

            //String encodedLink = URLEncoder.encode(link,"UTF-8");
            String encodedLink = link.replace(" ", "%20");
            Log.d("RDS Helper: ", encodedLink);

            URL url = new URL(encodedLink);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(encodedLink));
            HttpResponse response = client.execute(request);

            //BufferedReader in = new BufferedReader
            //        (new InputStreamReader(response.getEntity().getContent()));

            //StringBuffer sb = new StringBuffer("");
            ////String line="";
            //while ((line = in.readLine()) != null) {
            ////    sb.append(line);
            //    break;
            //}
            //in.close();
            //outputMessage = "success";
            //return sb.toString();
            outputMessage = "success";
            successOfRequest = true;
            return "success";
        } catch (Exception e) {
            //return new String("Exception: " + e.getMessage());
            errorMessage = "Exception: " + e.getMessage();
            outputMessage = "failure";
            successOfRequest = false;
            return "failure";
        }
    }

    @Override
    protected void onPostExecute(String result){
       Log.d("RDS Helper: ", "onPostExecute " + successOfRequest);
    }

    public boolean didRequestSucceed() {
        return successOfRequest;
    }
    public String getErrorMessage(){
        return errorMessage;
    }

    public String getOutputMessage() {return outputMessage; }

    public boolean insertUser(String... arg0){

        try{
            String lastName = (String)arg0[0];
            String firstName = (String)arg0[1];
            String age = (String)arg0[2];
            String gender = (String)arg0[3];
            String lookingFor = (String)arg0[4];
            String phoneNumber = (String)arg0[5];
            String tagLine = (String)arg0[6];

            Log.d("RDS Helper: ", lastName);
            Log.d("RDS Helper: ", firstName);
            Log.d("RDS Helper: ", age);
            Log.d("RDS Helper: ", gender);
            Log.d("RDS Helper: ", lookingFor);
            Log.d("RDS Helper: ", phoneNumber);
            Log.d("RDS Helper: ", tagLine);
            String link="http://54.148.130.198/insertUser.php?";
            link += "last_name=" + lastName;
            link += "&" + "first_name=" + firstName;
            link += "&" + "age=" + age;
            link += "&" + "gender=" + gender;
            link += "&" + "looking_for=" + lookingFor;
            link += "&" + "phone_number=" + phoneNumber;
            link += "&" + "tag_line=" + tagLine;

            Log.d("RDS Helper: ", link);

            //String encodedLink = URLEncoder.encode(link,"UTF-8");
            String encodedLink = link.replace(" ", "%20");
            Log.d("RDS Helper: ", encodedLink);


            HttpClient client = new DefaultHttpClient();
            //HttpGet request = new HttpGet(encodedLink);
            //request.setURI(new URI(encodedLink));
            Log.d("RDS Helper: ", "request");
            HttpResponse response = client.execute(new HttpGet(encodedLink));
            Log.d("RDS Helper: ", "EXECUTE REQUEST");
            BufferedReader in = new BufferedReader
                    (new InputStreamReader(response.getEntity().getContent()));
            Log.d("RDS Helper: ", "BUFFERED READER");
            StringBuffer sb = new StringBuffer("");
            String line="";
            while ((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }
            in.close();
            Log.d("RDS Helper: ", "CLOSE BUFFERED READER");
            outputMessage = sb.toString();
            return true;

        }catch(Exception e){
            Log.d("RDS Helper: ", "failed to insertUser");
            errorMessage = "Exception: " + e.getMessage();
            Log.d("RDS Helper: ", errorMessage);
            e.printStackTrace();
            return false;
        }


    }

    public boolean updateUser(String... arg0){
        try{
            String oldLastName = (String)arg0[0];
            String oldFirstName = (String)arg0[1];
            String oldAge = (String)arg0[2];
            String oldGender = (String)arg0[3];
            String oldLookingFor = (String)arg0[4];
            String oldPhoneNumber = (String)arg0[5];
            String oldTagLine = (String)arg0[6];

            String newLastName = (String)arg0[7];
            String newFirstName = (String)arg0[8];
            String newAge = (String)arg0[9];
            String newGender = (String)arg0[10];
            String newLookingFor = (String)arg0[11];
            String newPhoneNumber = (String)arg0[12];
            String newTagLine = (String)arg0[13];

            String link="http://54.148.130.198/updateUser.php";

            String data  = URLEncoder.encode("old_last_name", "UTF-8")
                    + "=" + URLEncoder.encode(oldLastName, "UTF-8");
            data += "&" + URLEncoder.encode("old_first_name", "UTF-8")
                    + "=" + URLEncoder.encode(oldFirstName, "UTF-8");
            data += "&" + URLEncoder.encode("old_age", "UTF-8")
                    + "=" + URLEncoder.encode(oldAge, "UTF-8");
            data += "&" + URLEncoder.encode("old_gender", "UTF-8")
                    + "=" + URLEncoder.encode(oldGender, "UTF-8");
            data += "&" + URLEncoder.encode("old_looking_for", "UTF-8")
                    + "=" + URLEncoder.encode(oldLookingFor, "UTF-8");
            data += "&" + URLEncoder.encode("old_phone_number", "UTF-8")
                    + "=" + URLEncoder.encode(oldPhoneNumber, "UTF-8");
            data += "&" + URLEncoder.encode("old_tag_line", "UTF-8")
                    + "=" + URLEncoder.encode(oldTagLine, "UTF-8");
            data += "&" + URLEncoder.encode("last_name", "UTF-8")
                    + "=" + URLEncoder.encode(newLastName, "UTF-8");
            data += "&" + URLEncoder.encode("first_name", "UTF-8")
                    + "=" + URLEncoder.encode(newFirstName, "UTF-8");
            data += "&" + URLEncoder.encode("age", "UTF-8")
                    + "=" + URLEncoder.encode(newAge, "UTF-8");
            data += "&" + URLEncoder.encode("gender", "UTF-8")
                    + "=" + URLEncoder.encode(newGender, "UTF-8");
            data += "&" + URLEncoder.encode("looking_for", "UTF-8")
                    + "=" + URLEncoder.encode(newLookingFor, "UTF-8");
            data += "&" + URLEncoder.encode("phone_number", "UTF-8")
                    + "=" + URLEncoder.encode(newPhoneNumber, "UTF-8");
            data += "&" + URLEncoder.encode("tag_line", "UTF-8")
                    + "=" + URLEncoder.encode(newTagLine, "UTF-8");
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true); //true is used for POST/PUT requests. false is for GET requests
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader reader = new BufferedReader (new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                sb.append(line);
                break;
            }
            outputMessage = sb.toString();
            return true;
        }catch(Exception e){
            Log.d("RDS Helper: ", "failed to updateUser");
            errorMessage = "Exception: " + e.getMessage();
            return false;
        }
    }

}
