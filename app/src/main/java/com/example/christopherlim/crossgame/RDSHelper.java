package com.example.christopherlim.crossgame;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * Created by ChristopherL on 12/5/2014.
 */
public class RDSHelper extends AsyncTask<String,Void,String> {

//public class RDSHelper {
    private Context context;
    private String errorMessage;
    private String outputMessage;
    private boolean successOfRequest;
    private int mode;
    //TextView requestSucceeded;

    TextView lastName;
    TextView firstName;
    TextView age;
    TextView gender;
    TextView lookingFor;
    TextView phoneNumber;
    TextView tagLine;
    String androidId;
    public RDSHelper(Context context, TextView lastName, TextView firstName, TextView age, TextView gender,
            TextView lookingFor, TextView phoneNumber, TextView tagLine, String androidId, int mode) {
        this.context = context;
        outputMessage = "";
        errorMessage = "";
        successOfRequest = false;
        this.lastName = (TextView) lastName;
        this.firstName = (TextView) firstName;
        this.age = (TextView) age;
        this.gender = (TextView) gender;
        this.lookingFor = (TextView) lookingFor;
        this.phoneNumber = (TextView) phoneNumber;
        this.tagLine = (TextView) tagLine;
        this.mode = mode;
        this.androidId = androidId;
    }

    @Override
    protected String doInBackground(String... arg0) {
        if(mode == 1) {
            //if we are inserting
            try {
                String lastName = (String) arg0[0];
                String firstName = (String) arg0[1];
                String age = (String) arg0[2];
                String gender = (String) arg0[3];
                String lookingFor = (String) arg0[4];
                String phoneNumber = (String) arg0[5];
                String tagLine = (String) arg0[6];
                String deviceId = (String) arg0[7];

                Log.d("RDS Helper: ", lastName);
                Log.d("RDS Helper: ", firstName);
                Log.d("RDS Helper: ", age);
                Log.d("RDS Helper: ", gender);
                Log.d("RDS Helper: ", lookingFor);
                Log.d("RDS Helper: ", phoneNumber);
                Log.d("RDS Helper: ", tagLine);
                Log.d("RDS Helper: ", deviceId);

                String link = "http://54.148.130.198/insertUser.php?";
                link += "last_name=" + lastName;
                link += "&" + "first_name=" + firstName;
                link += "&" + "age=" + age;
                link += "&" + "gender=" + gender;
                link += "&" + "looking_for=" + lookingFor;
                link += "&" + "phone_number=" + phoneNumber;
                link += "&" + "tag_line=" + tagLine;
                link += "&" + "device_id=" + deviceId;

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
        } else {
            //else we are getting information
            try {

                String firstName = (String) arg0[0];

                String link = "http://54.148.130.198/getProfile.php?";
                link += "device_id=" + androidId;
                link += "&" + "first_name=" + firstName;

                Log.d("RDS Helper: ", link);

                //String encodedLink = URLEncoder.encode(link,"UTF-8");
                String encodedLink = link.replace(" ", "%20");
                Log.d("RDS Helper: ", encodedLink);

                URL url = new URL(encodedLink);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(encodedLink));
                HttpResponse response = client.execute(request);

                /*
                BufferedReader in = new BufferedReader
                        (new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line="";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    Log.d("RDS Helper: " , sb.toString());
                    break;
                }
                in.close();
                Log.d("RDS Helper: " , sb.toString());
                */

                //outputMessage = "success";
                //successOfRequest = true;

                String str = EntityUtils.toString(response.getEntity());
                Log.d("RDS Helper: ",  str);





                 /*
                String username = "admin";
                String password = "admin";
                String link = "http://54.148.130.198/getMethod.php?username="
                        +username+"&password="+password;
                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);

                String str = EntityUtils.toString(response.getEntity());
                Log.d("RDS Helper: ",  str);*/

                return str;
            } catch (Exception e) {
                //return new String("Exception: " + e.getMessage());
                errorMessage = "Exception: " + e.getMessage();
                outputMessage = "failure";
                successOfRequest = false;
                return "failure";
            }

        }

    }

    @Override
    protected void onPostExecute(String result){
        if(mode == 1) {
            Log.d("RDS Helper: ", "onPostExecute " + successOfRequest);
        } else {
            Pattern pattern = Pattern.compile(Pattern.quote("<br>"));
            String[] data = pattern.split(result);
            //System.out.println(Arrays.toString(data));
            lastName.setText(data[1]);
            //lastName.setFocusable(false);
            //lastName.setClickable(false);

            firstName.setText(data[2]);
            //firstName.setFocusable(false);
            //firstName.setClickable(false);

            age.setText(data[3]);
            //age.setFocusable(false);
            //age.setClickable(false);

            gender.setText(data[4]);
            //gender.setFocusable(false);
            //gender.setClickable(false);

            lookingFor.setText(data[5]);
            //lookingFor.setFocusable(false);
            //lookingFor.setClickable(false);

            phoneNumber.setText(data[6]);
            //phoneNumber.setFocusable(false);
            //phoneNumber.setClickable(false);

            tagLine.setText(data[7]);
            //tagLine.setFocusable(false);
            //tagLine.setClickable(false);

        }

    }

    public boolean didRequestSucceed() {
        return successOfRequest;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public String getOutputMessage() {
        return outputMessage;
    }

}
