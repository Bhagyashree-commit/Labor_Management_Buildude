package com.example.labourmangement.Labour;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import com.example.labourmangement.Contractor.ContractorDashboard;
import com.example.labourmangement.DatabaseConfiguration.AppConfig;
import com.example.labourmangement.DatabaseHelper.SessionManager;
import com.example.labourmangement.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LabourDashboard extends AppCompatActivity {
    private static final String TAG = LabourDashboard.class.getSimpleName();
EditText labor_uname,labor_mobnum,labor_age,labor_address,labor_wages,labor_workinghours,labor_interestedarea;
String labornameholder,labormobnumholder,laborageholder,laborgenderholder,laborwageholder,laborinterestedon,laboraddressholder,laborworkinghourholder,laborspinnerholder,laborinterestedareaholder,labortransportholder;
RadioGroup rg_gender,rg_interestedon;
RadioButton rb_daily,rbmothly,rb_weekly,rb_male,rb_female;
    private SessionManager session;
    Button btnsubmit;
    MaterialBetterSpinner categoryspinner,modeoftransport,wageratespinner,workinghourspinner;
    int flag;
    ProgressDialog progressDialog;
    String[] SPINNER_DATA = {"General labor",
           "RCC carpenter",
            "RCC fitter",
            "Mason - sub category - brickwork, ucr masonry, waterproofing",
            "Plaster",
           "Tile fixer",
            "Plumber",
            "Fabricator",
            "Electrician",
            "POP worker",
            "Painter",
            "Furniture carpenter"};

    String[] SPINNERWAGERATE = {"300-400","400-500","500-600","600-700","700-800","800-900"};

    String[] SPINNERWORKINGHOUR = {"9AM-5PM","9:30AM-10:30PM","10AM-6PM","10:30AM-6:30PM"};

    String[] SPINNER_DATA_FORMODEOFTRANSPORT = {"Walk","Two Wheeler","Bus","Auto","Cab"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labour_dashboard);


        getSupportActionBar().setTitle("Labor Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        labor_uname = (EditText) findViewById(R.id.etlabour_username);
        labor_mobnum = (EditText) findViewById(R.id.etlabour_mobilenum);
        labor_age = (EditText) findViewById(R.id.etlabour_age);

        labor_address = (EditText) findViewById(R.id.etlabour_postaladdress);
        wageratespinner = (MaterialBetterSpinner) findViewById(R.id.wageratespinner);

        workinghourspinner = (MaterialBetterSpinner) findViewById(R.id.workinghourspinner);
        labor_interestedarea = (EditText) findViewById(R.id.etlabour_workingarea);
        modeoftransport = (MaterialBetterSpinner) findViewById(R.id.category_modeoftransport);
        btnsubmit = (Button) findViewById(R.id.button_submitlabordata);
        rg_gender = (RadioGroup) findViewById(R.id.radiogroupgender);
        rg_interestedon = (RadioGroup) findViewById(R.id.radiogroupinterestworkon);


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                flag = 0;
                if (labor_uname.getText().toString().equals("")) {
                    labor_uname.setError("Enter Username");
                    labor_uname.requestFocus();
                    flag = 1;
                }

                if (labor_address.getText().toString().length() == 0) {
                    labor_address.setError(" Enter Address");
                    labor_address.requestFocus();
                    flag = 1;
                }

                if (labor_age.getText().toString().length() == 0) {
                    labor_age.setError(" Enter Age");
                    labor_age.requestFocus();
                    flag = 1;
                }


                if (labor_interestedarea.getText().toString().length() == 0) {
                    labor_interestedarea.setError(" Enter Interested Area of work");
                    labor_interestedarea.requestFocus();
                    flag = 1;
                }
                if (flag == 0) {
                    getandset();
                }
                // getandset();
            }
        });

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);

        // email
        String email = user.get(SessionManager.KEY_EMAIL);

        labor_uname.setText(name);
        labor_mobnum.setText(email);

        categoryspinner = (MaterialBetterSpinner) findViewById(R.id.category_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LabourDashboard.this, android.R.layout.simple_dropdown_item_1line, SPINNER_DATA);

        categoryspinner.setAdapter(adapter);

        ArrayAdapter<String> adaptermodeoftransport = new ArrayAdapter<String>(LabourDashboard.this, android.R.layout.simple_dropdown_item_1line, SPINNER_DATA_FORMODEOFTRANSPORT);

        modeoftransport.setAdapter(adaptermodeoftransport);


        ArrayAdapter<String> adapterwagerate = new ArrayAdapter<String>(LabourDashboard.this, android.R.layout.simple_dropdown_item_1line, SPINNERWAGERATE);

        wageratespinner.setAdapter(adapterwagerate);

        ArrayAdapter<String> adapterwokinghour = new ArrayAdapter<String>(LabourDashboard.this, android.R.layout.simple_dropdown_item_1line, SPINNERWORKINGHOUR);

        workinghourspinner.setAdapter(adapterwokinghour);


        categoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //laborspinnerholder = categoryspinner.getSelectedItem().toString();
                laborspinnerholder = (String) parent.getItemAtPosition(position);
                Toast.makeText
                        (getApplicationContext(), "Selected : " + laborspinnerholder, Toast.LENGTH_SHORT)
                        .show();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        progressDialog = new ProgressDialog(LabourDashboard.this);

      /*  logout_btn = (Button) findViewById(R.id.btn_logoutlabor);


        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(LabourDashboard.this);
                alertDialog2.setTitle("Confirm Logout...");
                alertDialog2.setMessage("Are you sure! you want Logout?");

                alertDialog2.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        session.logoutUser();

                        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                        editor.clear();
                        editor.commit();
                        Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                        sendBroadcast(broadcastIntent);

                        Intent intent1 = new Intent(LabourDashboard.this, MainActivity.class);
                        intent1.putExtra("finish", true);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);

                    }
                });

                // Setting Negative "NO" Button
                alertDialog2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog2.show();


            }
        });
    }*/


    }
    private void getandset(){
        GetValueFromEditText();



        if(labor_uname.getText().toString().length()==0){
            labor_uname.setError(" Enter Valid Name");
            labor_uname.requestFocus();

        }


        if(labor_age.getText().toString().length()==0){
            labor_age.setError(" Enter Age");
            labor_age.requestFocus();

        }

        if(labor_address.getText().toString().length()==0){
            labor_address.setError(" Enter address");
            labor_address.requestFocus();

        }
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
       progressDialog.show();

        // Calling method to get value from EditText.
        //GetValueFromEditText();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_INSERTLABORDATA,new Response.Listener<String>() {
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
                            Toast.makeText(LabourDashboard.this, "Something wrong11", duration).show();
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            System.out.println("AuthFailureError.........................." + error);
                           // hideDialog();
                            progressDialog.dismiss();
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(LabourDashboard.this, "Something wrong22", duration).show();
                        } else if (error instanceof ServerError) {
                            System.out.println("server erroer......................." + error);
                            //hideDialog();
                            progressDialog.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(LabourDashboard.this, "Something wrong33", duration).show();
                            //TODO
                        } else if (error instanceof NetworkError) {
                            System.out.println("NetworkError........................." + error);
                            //hideDialog();
                            progressDialog.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(LabourDashboard.this, "Something wrong44", duration).show();
                            //TODO
                        } else if (error instanceof ParseError) {
                            System.out.println("parseError............................." + error);
                            //hideDialog();
                            progressDialog.dismiss();

                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(LabourDashboard.this, "Something wrong55", duration).show();
                            //TODO

                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                params.put("labor_name", labornameholder);
                params.put("labor_mobnum", labormobnumholder);
                params.put("labor_gender", laborgenderholder);
                params.put("labor_age", laborageholder);
                params.put("labor_address", laboraddressholder);
                params.put("labor_category", laborspinnerholder);
                params.put("labor_wagerate", laborwageholder);
                params.put("transport_mode", labortransportholder);
                params.put("interest_work", laborinterestedon);
                params.put("particular_area", laborinterestedareaholder);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(LabourDashboard.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

// Creating method to get value from EditText.
public void GetValueFromEditText(){
    labornameholder = labor_uname.getText().toString().trim();
    labormobnumholder = labor_mobnum.getText().toString().trim();
    laborageholder = labor_age.getText().toString().trim();
    laborwageholder = "400-500";
   //laborwageholder= wageratespinner.getSelectedItem().
    laborinterestedareaholder = labor_interestedarea.getText().toString().trim();
    laborworkinghourholder = "9AM-10AM";
    labortransportholder = "twoWheeler";
    laboraddressholder =labor_address.getText().toString().trim();
    laborspinnerholder="fitter";
    laborinterestedon = ((RadioButton) findViewById(rg_interestedon.getCheckedRadioButtonId())).getText().toString();
    laborgenderholder = ((RadioButton) findViewById(rg_gender.getCheckedRadioButtonId())).getText().toString();
//labornameholder = labor_uname.getText().toString().trim();

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
                this.setContentView(R.layout.activity_labour_dashboard);
                break;
            case R.id.hn:
                languageToLoad = "hi"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_labour_dashboard);
                break;
            case R.id.mar:
                languageToLoad = "mar"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_labour_dashboard);
                break;

          /*  case R.id.logout:
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(LabourDashboard.this);
                alertDialog2.setTitle("Confirm Logout...");
                alertDialog2.setMessage("Are you sure! you want Logout?");

                alertDialog2.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        session.logoutUser();

                        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                        editor.clear();
                        editor.commit();
                        Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                        sendBroadcast(broadcastIntent);

                        Intent intent1 = new Intent(LabourDashboard.this, MainActivity.class);
                        intent1.putExtra("finish", true);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);

                    }
                });

                // Setting Negative "NO" Button
                alertDialog2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog2.show();

                return true;*/
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
