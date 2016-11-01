package com.example.developping.simpletask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import org.json.JSONException;
import java.util.ArrayList;
import io.realm.Realm;

public class ProfileFragment extends Fragment {
    final int REQ_CODE_PICK_IMAGE= 1;

    Button button;
    ListView listView;
    LinearLayout linearLayout;
    GridView hobbiesGridView;
    ArrayAdapter<String> adapter;
    EditText name;
    EditText job;
    EditText about;
    EditText friends;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Context context;
    String[] outputStrArr;
    ImageView profilePicture;
    Boolean visit;
    Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        Context context = new ContextThemeWrapper(getActivity(), R.style.FragmentTheme);
        LayoutInflater localInflater = inflater.cloneInContext(context);
        View rootView = localInflater.inflate(R.layout.profile_fragment, parent, false);
        realm = Realm.getDefaultInstance();


        listView = (ListView)rootView.findViewById(R.id.hobbiesList);
        button = (Button) rootView.findViewById(R.id.savebutton);
        name = (EditText)rootView.findViewById(R.id.name);
        job = (EditText)rootView.findViewById(R.id.jobTitle);
        about = (EditText)rootView.findViewById(R.id.aboutMeText);
        friends = (EditText)rootView.findViewById(R.id.friends);
        profilePicture = (ImageView) rootView.findViewById(R.id.profileImg);
        linearLayout = (LinearLayout)rootView.findViewById(R.id.friendsLayout);
        hobbiesGridView = (GridView)rootView.findViewById(R.id.gridView);
        visit = false;

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedpreferences.edit();
        String[] hobbies = getResources().getStringArray(R.array.hobbies);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, hobbies);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);



        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SparseBooleanArray checked = listView.getCheckedItemPositions();
                ArrayList<String> selectedItems = new ArrayList<String>();
                for (int i = 0; i < checked.size(); i++) {
                    // Item position in adapter
                    int position = checked.keyAt(i);
                    if (checked.valueAt(i))
                        selectedItems.add(adapter.getItem(position));
                }

                outputStrArr = new String[selectedItems.size()];

                for (int i = 0; i < selectedItems.size(); i++) {
                    outputStrArr[i] = selectedItems.get(i);
                }

                String userName = (String) name.getText().toString().trim();
                String userJob = (String) job.getText().toString().trim();
                String userAbout = (String) about.getText().toString().trim();
                String userFriends = (String) friends.getText().toString().trim();


                // saving to realm
                realm.beginTransaction();
                User user = realm.createObject(User.class);
                user.setUserName(userName);
                user.setUserJob(userJob);
                user.setUserAbout(userAbout);
                user.setUserFriends(userFriends);
                user.setSelectedItems(outputStrArr);
                realm.commitTransaction();



                // Create a bundle object
                Bundle b = new Bundle();
                b.putStringArray("selectedItems", outputStrArr);
                b.putString("userName", userName);
                b.putString("userJob", userJob);
                b.putString("userAbout", userAbout);
                b.putString("userFriends", userFriends);
                JsonUtil json = new JsonUtil();
                try {
                   String jsonStr =  json.toJSon(b);
                    visit = true;
                    editor.putString("profileInfo",jsonStr);
                    editor.putBoolean("visit", visit);
                    editor.commit();
                    cleaning();
                    getFragmentManager().popBackStack();

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            } });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_CODE_PICK_IMAGE);
            }});


    }
    public void cleaning(){

        if (hobbiesGridView != null) {
            if (hobbiesGridView.getChildCount() > 0) {
                hobbiesGridView.removeAllViews();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("userName", (String) name.getText().toString().trim());
        savedInstanceState.putString("userJob", (String) job.getText().toString().trim());
        savedInstanceState.putString("userAbout", (String) about.getText().toString().trim());
        savedInstanceState.putString("userFriends", (String) friends.getText().toString().trim());
        savedInstanceState.putStringArray("selectedItems", outputStrArr);
        Log.d("inside on recreated",savedInstanceState.getString("userName"));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            Log.d("inside on recreated", "ghgadhhafahadygetgdrwfdsowibfkdw");
            name.setText(savedInstanceState.getString("userName"));
            job.setText(savedInstanceState.getString("userJob"));
            about.setText(savedInstanceState.getString("userAbout"));
            friends.setText(savedInstanceState.getString("userFriends"));

        }
    }


}




