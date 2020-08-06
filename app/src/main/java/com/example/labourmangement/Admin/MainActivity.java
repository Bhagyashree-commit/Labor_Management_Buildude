package com.example.labourmangement.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.labourmangement.Contractor.Register_contractor;
import com.example.labourmangement.DatabaseHelper.SessionManager;
import com.example.labourmangement.Labour.Register_labour;
import com.example.labourmangement.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    Button btnlogin;
    Button btn_registercontrctor,btn_registerlabor;


    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* FirebaseMessaging.getInstance().setAutoInitEnabled(true);


        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"token = "+token);//unistall kr app and punha run krok*/

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


      //  btnlogin = (Button) findViewById(R.id.button_loginapp);
        btn_registercontrctor = (Button) findViewById(R.id.btncontractor);
        btn_registerlabor = (Button) findViewById(R.id.btnlabour);
//  linktoregister = (Button) findViewById(R.id.linktoreister);


        btn_registercontrctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,
                        Register_contractor.class);
                startActivity(i);

            }
        });

        btn_registerlabor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        Register_labour.class);
                startActivity(i);

            }
        });


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
                this.setContentView(R.layout.activity_main);
                break;
            case R.id.hn:
                languageToLoad = "hi"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_main);
                break;
            case R.id.mar:
                languageToLoad = "mar"; // your language
                locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_main);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
