package com.example.labourmangement.Contractor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import com.example.labourmangement.DatabaseConfiguration.AppConfig;
import com.example.labourmangement.DatabaseHelper.SQLiteHandler;
import com.example.labourmangement.DatabaseHelper.SessionManager;
import com.example.labourmangement.DatabaseHelper.SessionManagerContractor;
import com.example.labourmangement.Labour.Register_labour;
import com.example.labourmangement.Labour.VerifyPhone;
import com.example.labourmangement.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Register_contractor extends AppCompatActivity {
    private static final String TAG = Register_contractor.class.getSimpleName();

    Button btnregister;
    TextView alreadyauser;
    EditText et_username,et_emailid,et_password ,et_mobilenumber,et_conpassword;
    // SqliteHelper sqliteHelper;
    private ProgressDialog pDialog;
    private SessionManagerContractor sessioncon;
    private SQLiteHandler db;
    String  namePattern = "[a-zA-Z]+";
    String emailPattern ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; //"[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    int flag =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_contractor);
        btnregister=(Button)findViewById(R.id.button_registerapp);
        alreadyauser=(TextView)findViewById(R.id.textView_existing_user);
        et_emailid=(EditText)findViewById(R.id.editTextregisteremail);
        et_password=(EditText)findViewById(R.id.editTextregisterpassword);
        et_username=(EditText)findViewById(R.id.editTextregistername);
        et_conpassword=(EditText)findViewById(R.id.editTextregisterconfirmpassword);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        sessioncon = new SessionManagerContractor(getApplicationContext());
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (sessioncon.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Register_contractor.this,
                    ContractorProfile.class);
            startActivity(intent);
            finish();
        }


        // Register Button Click event
        btnregister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = et_username.getText().toString().trim();
                String email = et_emailid.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String cpassword = et_conpassword.getText().toString().trim();

                flag=0;
                if(et_username.getText().toString().length()==0 || et_username.getText().toString().trim().matches(namePattern)){
                    et_username.setError(" User Name should be valid");
                    et_username.requestFocus();
                    flag=1;

                }
                flag=0;
                if(et_password.getText().toString().length()< 4 || et_password.length()>10){
                    et_password.setError(" Password should be between 4 to 10 character");
                    et_password.requestFocus();
                    flag=1;

                }

                flag=0;
                if(email.isEmpty() || email.length() < 10){
                    et_emailid.setError(" Mobile number should be valid");
                    et_emailid.requestFocus();
                    flag=1;

                }
                flag=0;
                if(cpassword.isEmpty() || !cpassword.equals(password))
                {

                    Toast.makeText(Register_contractor.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    flag=1;
                }
                if(flag==0){
                    registerUser(name,email,password);
                }

               /* if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    registerUser(name,email,password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }*/
            }
        });

        // Link to Login Screen
        alreadyauser.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        MainActivityContractorLogin.class);
                startActivity(i);
                finish();
            }
        });
    }






    private void registerUser(final String name, final String email,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTERCONTRACTOR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                Log.i("tagconvertstr", "["+response+"]");
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
                        Bundle extras = new Bundle();
                        extras.putString("email", email);
                        extras.putString("name", name);
                        // Launch login activity
                        Intent intent = new Intent(
                                Register_contractor.this,
                                VerifyPhoneContractor.class);
                        intent.putExtras(extras);
                        //  intent.putExtra(extras);
                        // intent.putExtra("name", name);
                        startActivity(intent);
                        finish();
                        // Launch login activity
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                /*Log.e(TAG, "Registration Error null: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();*/

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    System.out.println("time out and noConnection...................." + error);
                    pDialog.dismiss();
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(Register_contractor.this, "Something wrong11", duration).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                    System.out.println("AuthFailureError.........................." + error);

                    pDialog.dismiss();
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(Register_contractor.this, "Something wrong22", duration).show();
                } else if (error instanceof ServerError) {
                    System.out.println("server erroer......................." + error);
                    pDialog.dismiss();

                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(Register_contractor.this, "Something wrong33", duration).show();
                    //TODO
                } else if (error instanceof NetworkError) {
                    System.out.println("NetworkError........................." + error);

                    pDialog.dismiss();

                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(Register_contractor.this, "Something wrong44", duration).show();
                    //TODO
                } else if (error instanceof ParseError) {
                    System.out.println("parseError............................." + error);

                    pDialog.dismiss();

                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(Register_contractor.this, "Something wrong55", duration).show();
                    //TODO

                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        // AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        // requestQueue.add(strReq);
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(strReq);
    }

        private void showDialog() {
            if (!pDialog.isShowing())
                pDialog.show();
        }

        private void hideDialog() {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.eng:
                String languageToLoad = "en"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_register_contractor);
                break;
            case R.id.hn:
                languageToLoad = "hi"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_register_contractor);
                break;
            case R.id.mar:
                languageToLoad = "mar"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_register_contractor);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
