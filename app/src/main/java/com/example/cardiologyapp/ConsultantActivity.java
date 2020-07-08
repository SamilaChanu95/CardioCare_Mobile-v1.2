package com.example.cardiologyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class ConsultantActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    //defining views
    ProgressBar progressBar;
    ListView listView;

    //we will use this list to display hero in list view
    List<Consultant> consultantList;

    //as the same button is used for create and update
    //we need to track whether it is an update or create operation
    //for this we have this boolean
    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant);

        progressBar = findViewById(R.id.progressBar);

        listView = findViewById(android.R.id.list);

        consultantList = new ArrayList<>();


        //calling the method read heroes to read existing consultant from the database
        //method is commented because it is not yet created
        readConsultant();
    }


    private void readConsultant() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_CONSULTANT, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshConsultantList(JSONArray consultants) throws JSONException {
        //clearing previous consultants
        consultantList.clear();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < consultants.length(); i++) {
            //getting each consultant object
            JSONObject obj = consultants.getJSONObject(i);

            //adding the hero to the list
            consultantList.add(new Consultant(
                    obj.getInt("id"),
                    obj.getString("c_nic"),
                    obj.getString("c_first_name"),
                    obj.getString("c_last_name"),
                    obj.getString("c_gender"),
                    obj.getString("c_address"),
                    obj.getString("c_dob"),
                    obj.getString("c_phone_number"),
                    obj.getString("c_role"),
                    obj.getString("c_status"),
                    obj.getInt("hospital_id"),
                    obj.getInt("department_id"),
                    obj.getInt("unit_id"),
                    obj.getInt("ward_id")
            ));
        }

        //creating the adapter and setting it to the list view
        ConsultantAdapter adapter = new ConsultantAdapter(consultantList);
        listView.setAdapter(adapter);
    }

    //inner class to perform network request extending an AsyncTask
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //when the task started displaying a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    //refreshing the consultantlist after every operation
                    //so we get an updated list
                    //we will create this method right now it is commented
                    //because we haven't created it yet
                    refreshConsultantList(object.getJSONArray("consultants"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

    class ConsultantAdapter extends ArrayAdapter<Consultant> {

        //our consultant list
        List<Consultant> consultantList;


        //constructor to get the list
        public ConsultantAdapter(List<Consultant> consultantList) {
            super(ConsultantActivity.this, R.layout.layout_consultant_list, consultantList);
            this.consultantList = consultantList;
        }

        //method returning list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_consultant_list, null, true);

            //getting the textview for displaying name
            TextView textViewcFirstName = listViewItem.findViewById(R.id.textViewName);
            TextView textViewcAddress = listViewItem.findViewById(R.id.textViewAddress);
            TextView textViewcPhoneNumber = listViewItem.findViewById(R.id.textViewPhoneNumber);
            TextView textViewcStatus = listViewItem.findViewById(R.id.textViewStatus);

            final Consultant consultant = consultantList.get(position);

            textViewcFirstName.setText(consultant.getC_first_name());
            textViewcAddress.setText(consultant.getC_address());
            textViewcPhoneNumber.setText(consultant.getC_phone_number());
            textViewcStatus.setText(consultant.getC_status());

            return listViewItem;
        }
    }
}
