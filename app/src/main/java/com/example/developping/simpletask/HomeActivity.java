package com.example.developping.simpletask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class HomeActivity extends AppCompatActivity {
    final int REQ_CODE_PICK_IMAGE = 65537;

    Button follow, contact;
    TextView name, job, about;
    Toolbar toolbar;
    GradientDrawable blue, red;
    DrawerLayout mDrawer;
    NavigationView nvDrawer;
    ImageView icon;
    ProfileFragment fragment;
    SharedPreferences sharedpreferences;
    LinearLayout friendsLayout;
    SharedPreferences.Editor editor;
    ImageView profPicture;
    Context context;
    Boolean visit;
    int repeated;
    ArrayList<String> hobbiesList;
    GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        repeated= 1;
        context = getApplicationContext();
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();

        hobbiesList = new ArrayList<String>();
        gridView = (GridView) findViewById(R.id.gridView);

        follow = (Button) findViewById(R.id.follow);
        contact = (Button) findViewById(R.id.contact);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        friendsLayout = (LinearLayout) findViewById(R.id.friendsLayout);
        profPicture = (ImageView) findViewById(R.id.profilePicture);

        toolbar.setNavigationIcon(R.drawable.back);

        blue = (GradientDrawable) follow.getBackground();
        blue.setColor(ContextCompat.getColor(this, R.color.buttonBlue));
        red = (GradientDrawable) contact.getBackground();
        red.setColor(ContextCompat.getColor(this, R.color.buttonRed));

        name = (TextView) findViewById(R.id.userName);
        job = (TextView) findViewById(R.id.job);
        about = (TextView) findViewById(R.id.aboutText);



        icon = (ImageView) findViewById(R.id.toolRightIcon);
        icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fragment = new ProfileFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.drawer_layout, fragment);
                ft.addToBackStack(null);
                ft.commit();
                getSupportFragmentManager().addOnBackStackChangedListener(getListener());
            }
        });

    }


    @Override

    protected void onPostCreate(Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

    }

    public void jsonDeserialization() throws JSONException {
        String jsonString = sharedpreferences.getString("profileInfo", null);
        JSONObject jsonObj = new JSONObject(jsonString);

        String username = jsonObj.getString("name");
        String userjob = jsonObj.getString("job");
        String userabout = jsonObj.getString("about");
        String userfriends = jsonObj.getString("friends");

        name.setText(username);
        job.setText(userjob);
        about.setText(userabout);
        if (userfriends != null){
            Log.d("the friends not null", userfriends);
            setFriends(userfriends);
        }
        JSONArray hobbiesArray = jsonObj.getJSONArray("hobbies");
        for (int i = 0; i < hobbiesArray.length(); i++) {
            JSONObject obj = hobbiesArray.getJSONObject(i);
            drawHobby(obj, i);
        }
        // hobbies arrayList is ready
        Log.d("hobbies list", hobbiesList.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.grid_view_item, hobbiesList);
        gridView.setAdapter(adapter);
        visit = false;
        editor.putBoolean("visit", visit);
        editor.commit();

    }

    public void drawHobby(JSONObject obj, int i) throws JSONException {
        String hobby = obj.getString("" + i);
        hobbiesList.add(hobby);

    }


    private OnBackStackChangedListener getListener() {
        OnBackStackChangedListener result = new OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager manager = getSupportFragmentManager();

                if (manager != null) {
                    visit = sharedpreferences.getBoolean("visit", false);
                    if (visit == true) {
                          if (repeated == 1) {
                              try {
                                  String jsonString = sharedpreferences.getString("profileInfo", null);
                                  JSONObject jsonObj = new JSONObject(jsonString);
                                  if (jsonObj != null) {
                                      cleaning();
                                      jsonDeserialization();
                                  }
                              } catch (JSONException ex) {
                                  ex.printStackTrace();
                              }
                          }
                    }
                }
            }
        };

        return result;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case REQ_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri uri = imageReturnedIntent.getData();

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                        BitmapShader shader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
                        Paint paint = new Paint();
                        paint.setShader(shader);
                        paint.setAntiAlias(true);
                        Canvas c = new Canvas(circleBitmap);
                        c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
                        profPicture.setImageBitmap(circleBitmap);
                        editor.putString("profileImg", bitmap.toString());
                        ImageView fragmentImgView = (ImageView) findViewById(R.id.profileImg);
                        fragmentImgView.setImageBitmap(circleBitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }


    }

    public void setFriends(String friendsList) throws JSONException {
        String[] separated = friendsList.split(" ");
        for (int i = 0; i < separated.length; i++) {
            if (separated[i].isEmpty()) {

            }else{
                char first = separated[i].charAt(0);
                Log.d("the char", ""+first);
                TextView view = new TextView(this);
                view.setWidth(150);
                view.setHeight(150);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.setMargins(5, 10, 7, 5);
                view.setLayoutParams(params);
                view.setBackgroundResource(R.drawable.circle);
                view.setGravity(Gravity.CENTER);
                GradientDrawable drawable = (GradientDrawable) view.getBackground();
                Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                drawable.setColor(color);
                view.setTextSize(26);
                view.setText("" + first);
                friendsLayout.addView(view);
            }
        }
    }

    public void cleaning(){
        if(!hobbiesList.isEmpty()) {
            hobbiesList.clear();
        }
        if (friendsLayout != null) {
            Log.d("friends test"," do u remove all views");
            friendsLayout.removeAllViews();

        }

    }

}


