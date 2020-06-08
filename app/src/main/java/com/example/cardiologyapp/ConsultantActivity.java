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
    EditText editTextCNIC, editTextCFirstName, editTextCLastName,
            editTextCAddress, editTextCDOB, editTextCPhoneNumber,
            editTextHospital, editTextDepartment, editTextUnit,
            editTextWard, editTextCId;
    Spinner spinnerCGender, spinnerCRole, spinnerCStatus;
    ProgressBar progressBar;
    ListView listView;
    Button buttonAddUpdate;

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

        editTextCId = findViewById(R.id.editTextCId);
        editTextCNIC = findViewById(R.id.editTextCNIC);
        editTextCFirstName = findViewById(R.id.editTextCFirstName);
        editTextCLastName = findViewById(R.id.editTextCLastName);
        editTextCPhoneNumber = findViewById(R.id.editTextCPhoneNumber);
        editTextCAddress = findViewById(R.id.editTextCAddress);
        editTextHospital = findViewById(R.id.editTextHospital);
        editTextUnit = findViewById(R.id.editTextUnit);
        editTextDepartment = findViewById(R.id.editTextDepartment);
        editTextWard = findViewById(R.id.editTextWard);
        editTextCDOB = findViewById(R.id.editTextCDOB);
        spinnerCGender = findViewById(R.id.spinnerCGender);
        spinnerCRole = findViewById(R.id.spinnerCRole);
        spinnerCStatus = findViewById(R.id.spinnerCStatus);

        buttonAddUpdate = findViewById(R.id.buttonAddUpdate);

        progressBar = findViewById(R.id.progressBar);

        listView = findViewById(R.id.listViewConsultant);

        consultantList = new ArrayList<>();

        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if it is updating
                if (isUpdating) {
                    //calling the method update consultant
                    //method is commented because it is not yet created
                    updateConsultant();
                } else {
                    //if it is not updating
                    //that means it is creating
                    //so calling the method create consultant
                    createConsultant();
                }
            }
        });

        //calling the method read heroes to read existing consultant from the database
        //method is commented because it is not yet created
        readConsultant();
    }

    private void deleteConsultant(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_CONSULTANT + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void readConsultant() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_CONSULTANT, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void updateConsultant() {
        String id = editTextCId.getText().toString();
        String cNIC = editTextCNIC.getText().toString().trim();
        String cFirstName = editTextCFirstName.getText().toString().trim();
        String cLastName = editTextCLastName.getText().toString().trim();
        String cGender = spinnerCGender.getSelectedItem().toString();
        String cAddress = editTextCAddress.getText().toString().trim();
        String cDOB = editTextCDOB.getText().toString().trim();
        String cPhoneNumber = editTextCPhoneNumber.getText().toString().trim();
        String cRole = spinnerCRole.getSelectedItem().toString();
        String cStatus = spinnerCStatus.getSelectedItem().toString();

        int hospital = Integer.parseInt(editTextHospital.getText().toString());
        int department = Integer.parseInt(editTextDepartment.getText().toString());
        int unit = Integer.parseInt(editTextUnit.getText().toString());
        int ward = Integer.parseInt(editTextWard.getText().toString());

        //int rating = (int) ratingBar.getRating();
        //String team = spinnerTeam.getSelectedItem().toString();

        //validating the inputs
        if (TextUtils.isEmpty(cNIC)) {
            editTextCNIC.setError("Please enter NIC");
            editTextCNIC.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(cFirstName)) {
            editTextCFirstName.setError("Please enter First name");
            editTextCFirstName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(cLastName)) {
            editTextCLastName.setError("Please enter Last name");
            editTextCLastName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(cAddress)) {
            editTextCAddress.setError("Please enter Address");
            editTextCAddress.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(cDOB)) {
            editTextCDOB.setError("Please enter DOB");
            editTextCDOB.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(cPhoneNumber)) {
            editTextCPhoneNumber.setError("Please enter Phone Number");
            editTextCPhoneNumber.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("c_nic", cNIC);
        params.put("c_first_name", cFirstName);
        params.put("c_last_name", cLastName);
        params.put("c_gender", cGender);
        params.put("c_address", cAddress);
        params.put("c_dob", cDOB);
        params.put("c_phone_number", cPhoneNumber);
        params.put("c_role", cRole);
        params.put("c_status", cStatus);
        params.put("hospital_id", String.valueOf(hospital));
        params.put("department_id", String.valueOf(department));
        params.put("unit_id", String.valueOf(unit));
        params.put("ward_id", String.valueOf(ward));


        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_CONSULTANT, params, CODE_POST_REQUEST);
        request.execute();

        buttonAddUpdate.setText("Add");

        editTextCNIC.setText("");
        editTextCFirstName.setText("");
        editTextCLastName.setText("");
        spinnerCGender.setSelection(0);
        editTextCAddress.setText("");
        editTextCDOB.setText("");
        editTextCPhoneNumber.setText("");
        spinnerCRole.setSelection(0);
        spinnerCStatus.setSelection(0);
        editTextHospital.setText("");
        editTextDepartment.setText("");
        editTextUnit.setText("");
        editTextWard.setText("");
        //editTextWard.setText(String.valueOf(1));

        //ratingBar.setRating(0);
        //spinnerTeam.setSelection(0);

        isUpdating = false;
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

    private void createConsultant() {

        String cNIC = editTextCNIC.getText().toString().trim();
        String cFirstName = editTextCFirstName.getText().toString().trim();
        String cLastName = editTextCLastName.getText().toString().trim();
        String cGender = spinnerCGender.getSelectedItem().toString();
        String cAddress = editTextCAddress.getText().toString().trim();
        String cDOB = editTextCDOB.getText().toString().trim();
        String cPhoneNumber = editTextCPhoneNumber.getText().toString().trim();
        String cRole = spinnerCRole.getSelectedItem().toString();
        String cStatus = spinnerCStatus.getSelectedItem().toString();

        //int hospital_id = (int) editTextHospital.getHospital_id();

        int hospital = Integer.parseInt(editTextHospital.getText().toString());
        int department = Integer.parseInt(editTextDepartment.getText().toString());
        int unit = Integer.parseInt(editTextUnit.getText().toString());
        int ward = Integer.parseInt(editTextWard.getText().toString());

        //String Hospital = editTextHospital.getText().toString();
        //int cWard = Integer.parseInt(ward);

        //validating the inputs
        if (TextUtils.isEmpty(cNIC)){
            editTextCNIC.setError("Please enter NIC");
            editTextCNIC.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(cFirstName)) {
            editTextCFirstName.setError("Please enter First name");
            editTextCFirstName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(cLastName)) {
            editTextCLastName.setError("Please enter Last name");
            editTextCLastName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(cAddress)) {
            editTextCAddress.setError("Please enter Address");
            editTextCAddress.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(cDOB)) {
            editTextCDOB.setError("Please enter DOB");
            editTextCDOB.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(cPhoneNumber)) {
            editTextCPhoneNumber.setError("Please enter Phone Number");
            editTextCPhoneNumber.requestFocus();
            return;
        }

        //if validation passes

        HashMap<String, String> params = new HashMap<>();
        params.put("c_nic", cNIC);
        params.put("c_first_name", cFirstName);
        params.put("c_last_name", cLastName);
        params.put("c_gender", cGender);
        params.put("c_address", cAddress);
        params.put("c_dob", cDOB);
        params.put("c_phone_number", cPhoneNumber);
        params.put("c_role", cRole);
        params.put("c_status", cStatus);
        params.put("hospital_id", String.valueOf(hospital));
        params.put("department_id", String.valueOf(department));
        params.put("unit_id", String.valueOf(unit));
        params.put("ward_id", String.valueOf(ward));


        //Calling the create hero API
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_CONSULTANT, params, CODE_POST_REQUEST);
        request.execute();

        buttonAddUpdate.setText("Add");

        editTextCNIC.setText("");
        editTextCFirstName.setText("");
        editTextCLastName.setText("");
        spinnerCGender.setSelection(0);
        editTextCAddress.setText("");
        editTextCDOB.setText("");
        editTextCPhoneNumber.setText("");
        spinnerCRole.setSelection(0);
        spinnerCStatus.setSelection(0);
        editTextHospital.setText("");
        editTextDepartment.setText("");
        editTextUnit.setText("");
        editTextWard.setText("");
        //editTextWard.setText(String.valueOf(1));
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

            //the update and delete textview
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final Consultant consultant = consultantList.get(position);

            textViewcFirstName.setText(consultant.getC_first_name());

            //attaching click listener to update
            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //so when it is updating we will
                    //make the isUpdating as true
                    isUpdating = true;

                    //we will set the selected consultant to the UI elements
                    editTextCId.setText(String.valueOf(consultant.getId()));
                    editTextCNIC.setText(consultant.getC_nic());
                    editTextCFirstName.setText(consultant.getC_first_name());
                    editTextCLastName.setText(consultant.getC_last_name());
                    spinnerCGender.setSelection(((ArrayAdapter<String>) spinnerCGender.getAdapter()).getPosition(consultant.getC_gender()));
                    editTextCAddress.setText(consultant.getC_address());
                    editTextCDOB.setText(consultant.getC_dob());
                    editTextCPhoneNumber.setText(consultant.getC_phone_number());
                    spinnerCRole.setSelection(((ArrayAdapter<String>) spinnerCRole.getAdapter()).getPosition(consultant.getC_role()));
                    spinnerCStatus.setSelection(((ArrayAdapter<String>) spinnerCStatus.getAdapter()).getPosition(consultant.getC_status()));
                    editTextHospital.setText(String.valueOf(consultant.getHospital_id()));
                    editTextDepartment.setText(String.valueOf(consultant.getDepartment_id()));
                    editTextUnit.setText(String.valueOf(consultant.getUnit_id()));
                    editTextWard.setText(String.valueOf(consultant.getWard_id()));

                    //ratingBar.setRating(hero.getRating());

                    //we will also make the button text to Update
                    buttonAddUpdate.setText("Update");
                }
            });

            //when the user selected delete
            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // we will display a confirmation dialog before deleting
                    AlertDialog.Builder builder = new AlertDialog.Builder(ConsultantActivity.this);

                    builder.setTitle("Delete " + consultant.getC_first_name())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //if the choice is yes we will delete the hero
                                    //method is commented because it is not yet created
                                    deleteConsultant(consultant.getId());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });

            return listViewItem;
        }
    }
}
