package com.example.ruaasamara.simpletask;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
import android.content.Context;
import android.util.Log;
import android.preference.PreferenceManager;


public class SignUpActivity extends AppCompatActivity {
    Button registerButton, signInWithFB;
    EditText userName, userEmail, userPassword;
    TextView signUp;
    Toast toast;
    GradientDrawable blue,black;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        context = getApplicationContext();
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedpreferences.edit();
        toast = new Toast(this);
        TextView signUp = (TextView) findViewById(R.id.signup);
        registerButton = (Button) findViewById(R.id.register);
        signInWithFB = (Button) findViewById(R.id.signInWithFaceBook);
        userName=(EditText)findViewById(R.id.userName);
        userEmail=(EditText)findViewById(R.id.email);
        userPassword=(EditText)findViewById(R.id.password);




        blue = (GradientDrawable) registerButton.getBackground();
        blue.setColor(ContextCompat.getColor(this, R.color.buttonBlue));
        black = (GradientDrawable) signInWithFB.getBackground();
        black.setColor(ContextCompat.getColor(this, R.color.buttonBlack));






        registerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String email  = userEmail.getText().toString();
                String password  = userPassword.getText().toString();
                if(!sharedpreferences.contains(email)){
                    editor.putString(email, password);
                    editor.commit();
                    Log.i("SignUp activity", sharedpreferences.getString(email,null));
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                } else {

                    toast.makeText(context, "This email is already exists, plz try anthor one.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        signInWithFB.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                toast.makeText(context, "You need to signIn to your Facebook account. ",
                        Toast.LENGTH_LONG).show();

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);


            }
        });


    }
}
