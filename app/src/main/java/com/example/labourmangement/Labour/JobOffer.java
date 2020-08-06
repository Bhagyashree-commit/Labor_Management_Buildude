package com.example.labourmangement.Labour;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.labourmangement.Adapter.JobAdapter;
import com.example.labourmangement.DatabaseConfiguration.AppConfig;
import com.example.labourmangement.DatabaseConfiguration.SharedPrefManager;
import com.example.labourmangement.R;
import com.example.labourmangement.model.JobModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class JobOffer extends AppCompatActivity implements JobAdapter.OnItemClickListener {
    private static final String TAG = JobOffer.class.getSimpleName();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
Button buttonDisplayToken;
TextView textViewToken;
    List<JobModel> jobmodellist;

    RequestQueue rq;
   // public  static final String EXTRA_URL = " image_path";
    public  static  final  String EXTRA_TITLE = "job_title";
    public  static  final  String EXTRA_DETAILS = "job_details";
    public  static  final  String EXTRA_WAGES = "job_wages";
    public  static  final  String EXTRA_AREA = "job_area";
    public  static  final  String EXTRA_ID = "job_id";
  //  public  static  final  String EXTRA_DESCRIPTION = "product_description";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_offer);

        getSupportActionBar().setTitle("Job Offers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //getting views from xml
        textViewToken = (TextView) findViewById(R.id.textViewToken);
        buttonDisplayToken = (Button) findViewById(R.id.buttonDisplayToken);

        buttonDisplayToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == buttonDisplayToken) {
                    //getting token from shared preferences
                    String token = SharedPrefManager.getInstance(JobOffer.this).getDeviceToken();

                    //if token is not null
                    if (token != null) {
                        //displaying the token
                        textViewToken.setText(token);
                    } else {
                        //if token is null that means something wrong
                        textViewToken.setText("Token not generated");
                    }
                }
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        jobmodellist = new ArrayList<>();

        sendRequest();
    }


    public void sendRequest(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.URL_GETJOBOFFER,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());

                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            Log.d(TAG,array.toString());
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject job = array.getJSONObject(i);


                               /* jobmodellist.add(new JobModel(
                                        jobModel.setString("job_title"),
                                        job.getString("job_details"),
                                        job.getString("job_wages"),
                                        job.getString("job_area")
                                ));*/
                                //Log.d(TAG,"job 61"+job);
                                JobModel jobModel = new JobModel();
                                //adding the product to product list
                                jobModel.setJob_area(job.getString("job_area"));
                                      jobModel.setJob_title(  job.getString("job_title"));
                                       jobModel.setJob_details( job.getString("job_details"));
                                       jobModel.setJob_wages( job.getString("job_wages"));
                                jobModel.setJob_id( job.getString("job_id"));

                              jobmodellist.add(jobModel);
                            }

                            Log.d(TAG,"jobgggggggggggggg"+jobmodellist.size());
                            //creating adapter object and setting it to recyclerview
                            JobAdapter adapter = new JobAdapter(JobOffer.this, jobmodellist);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
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
                this.setContentView(R.layout.activity_job_offer);
                break;
            case R.id.hn:
                languageToLoad = "hi"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_job_offer);
                break;
            case R.id.mar:
                languageToLoad = "mar"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_job_offer);
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

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this,JobDetails.class);
        JobModel clickedItem=jobmodellist.get(position);
    //   detailIntent.putExtra(EXTRA_URL,clickedItem.getImage_path());
        detailIntent.putExtra(EXTRA_TITLE,clickedItem.getJob_title());
        detailIntent.putExtra(EXTRA_DETAILS,clickedItem.getJob_details());
        detailIntent.putExtra(EXTRA_WAGES,clickedItem.getJob_wages());
        detailIntent.putExtra(EXTRA_AREA,clickedItem.getJob_area());
        detailIntent.putExtra(EXTRA_ID,clickedItem.getJob_id());


        startActivity(detailIntent);

    }
}
