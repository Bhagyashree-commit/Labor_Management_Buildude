package com.example.labourmangement.Contractor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.labourmangement.Admin.MainActivity;
import com.example.labourmangement.DatabaseConfiguration.AppConfig;
import com.example.labourmangement.DatabaseHelper.SessionManager;
import com.example.labourmangement.DatabaseHelper.SessionManagerContractor;
import com.example.labourmangement.Labour.LabourDashboard;
import com.example.labourmangement.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ContractorDashboard extends AppCompatActivity {
    private static final String TAG = ContractorDashboard.class.getSimpleName();
    EditText editText_uname,editText_mobnum,editText_address,editText_areaofoperation,editText_workinghour;
    Button btnsubmitcontractor,logout;
RadioGroup rg_interestedon;
String etunameholder,etmobnumholder,etaddressholder,etareaofoperationholder,etworkinghourholder,etradiogroholder;
int flag;
MaterialBetterSpinner category_workinghours;
    ProgressDialog progressDialog;

    String[] SPINNERWORKINGHOUR = {"9AM-5PM","10AM-6PM","9:30AM-5:30PM","10:30AM-6:30PM"};


    private SessionManagerContractor sessioncon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_dashboard);

        getSupportActionBar().setTitle("Contractor Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        editText_uname = (EditText) findViewById(R.id.etcontractor_username);
        editText_mobnum = (EditText) findViewById(R.id.etcontractor_mobilenum);
        editText_address=(EditText)findViewById(R.id.etcontractor_postaladdress);
        editText_areaofoperation=(EditText)findViewById(R.id.etcontractor_areaofoperation);
        category_workinghours=(MaterialBetterSpinner)findViewById(R.id.category_workinghours);
        rg_interestedon=(RadioGroup)findViewById(R.id.rg_interestedonworking);
        btnsubmitcontractor=(Button)findViewById(R.id.button_submitcontractor);


        ArrayAdapter<String> adapterworkinghours = new ArrayAdapter<String>(ContractorDashboard.this, android.R.layout.simple_dropdown_item_1line, SPINNERWORKINGHOUR);

        category_workinghours.setAdapter(adapterworkinghours);

        //logout=(Button)findViewById(R.id.btn_logoutcontractor);
        sessioncon = new SessionManagerContractor(getApplicationContext());
        sessioncon.checkLogin();
        HashMap<String, String> user = sessioncon.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);

        // email
        String email = user.get(SessionManager.KEY_EMAIL);

        editText_uname.setText(name);
        editText_mobnum.setText(email);

        progressDialog = new ProgressDialog(ContractorDashboard.this);
        btnsubmitcontractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flag=0;
                if(editText_uname.getText().toString().equals("")){
                    editText_uname.setError("Enter Username");
                    editText_uname.requestFocus();
                    flag=1;
                }

                if(editText_address.getText().toString().length()==0){
                    editText_address.setError(" Enter Address");
                    editText_address.requestFocus();
                    flag=1;
                }

                if(editText_areaofoperation.getText().toString().length()==0){
                    editText_areaofoperation.setError(" Enter Area Of Operation");
                    editText_areaofoperation.requestFocus();
                    flag=1;
                }
                if(flag==0){
                    savedata();
                }

            }
        });

    }

    private void savedata(){
        GetValueFromEditText();
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_INSERTCONTRACTORDATA,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Inserting Response: " + response.toString());
                progressDialog.dismiss();
                //hideDialog();
                Log.i("tagconvertstr", "["+response+"]");
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Toast.makeText(getApplicationContext(),
                            jsonObject.getString("message")+response,
                            Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            System.out.println("time out and noConnection...................." + error);
                            progressDialog.dismiss();
                            // hideDialog();
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(ContractorDashboard.this, "Something wrong11", duration).show();
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            System.out.println("AuthFailureError.........................." + error);
                            // hideDialog();
                            progressDialog.dismiss();
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(ContractorDashboard.this, "Something wrong22", duration).show();
                        } else if (error instanceof ServerError) {
                            System.out.println("server erroer......................." + error);
                            //hideDialog();
                            progressDialog.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(ContractorDashboard.this, "Something wrong33", duration).show();
                            //TODO
                        } else if (error instanceof NetworkError) {
                            System.out.println("NetworkError........................." + error);
                            //hideDialog();
                            progressDialog.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(ContractorDashboard.this, "Something wrong44", duration).show();
                            //TODO
                        } else if (error instanceof ParseError) {
                            System.out.println("parseError............................." + error);
                            //hideDialog();
                            progressDialog.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(ContractorDashboard.this, "Something wrong55", duration).show();
                            //TODO

                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                params.put("contractor_name", etunameholder);
                params.put("contractor_mobnum", etmobnumholder);
                params.put("contractor_areaofoperation", etareaofoperationholder);
                params.put("contractor_address", etaddressholder);
                params.put("working_hours", etworkinghourholder);
                params.put("interested_on", etradiogroholder);


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(ContractorDashboard.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    // Creating method to get value from EditText.
    public void GetValueFromEditText(){
        etunameholder = editText_uname.getText().toString().trim();
        etmobnumholder = editText_mobnum.getText().toString().trim();
        etaddressholder = editText_address.getText().toString().trim();
        etworkinghourholder = "9AM-5PM";
        etareaofoperationholder = editText_areaofoperation.getText().toString().trim();
        etradiogroholder = ((RadioButton) findViewById(rg_interestedon.getCheckedRadioButtonId())).getText().toString();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        switch (item.getItemId()) {
            case R.id.eng:
                String languageToLoad = "en"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_contractor_dashboard);
                break;
            case R.id.hn:
                languageToLoad = "hi"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_contractor_dashboard);
                break;
            case R.id.mar:
                languageToLoad = "mar"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_contractor_dashboard);
                break;



            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
