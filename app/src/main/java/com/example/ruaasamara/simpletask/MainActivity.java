package com.example.ruaasamara.simpletask;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.widget.Toast;
import android.content.Context;

public class MainActivity extends AppCompatActivity {
    Button start;
    TextView signUp;
    GradientDrawable blue;
    EditText email, password;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Toast toast;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedpreferences.edit();
        toast = new Toast(this);
        start = (Button) findViewById(R.id.login);
        signUp = (TextView) findViewById(R.id.signup);




        blue = (GradientDrawable) start.getBackground();
        blue.setColor(ContextCompat.getColor(this, R.color.buttonBlue));






        // Listening to register new account link
        start.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                email = (EditText) findViewById(R.id.email);
                password = (EditText) findViewById(R.id.password);
                String useremail = email.getText().toString();
                String userpassword = password.getText().toString();

                if(useremail.trim().length() > 0 && userpassword.trim().length() > 0){
                    if(sharedpreferences.contains(useremail)){
                        if((sharedpreferences.getString(useremail,null)).equals(userpassword)){
                            // Switching to Register screen
                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(i);
                        }
                    } else {

                        toast.makeText(context, "This email is not valid, plz try anthor one.",
                                Toast.LENGTH_LONG).show();
                    }

                }

            }
        });




        // Listening to register new account link
        signUp.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);


            }
        });
    }
}

