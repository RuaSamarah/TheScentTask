package com.example.developping.simpletask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    final int REQ_CODE_PICK_IMAGE= 1;

    Button button;
    ListView listView;
    ArrayAdapter<String> adapter;
    EditText name;
    EditText job;
    EditText about;
    EditText friends;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Context context;
    ImageView profilePicture;
    Boolean visit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_fragment, parent, false);

        listView = (ListView)rootView.findViewById(R.id.hobbiesList);
        button = (Button) rootView.findViewById(R.id.savebutton);
        name = (EditText)rootView.findViewById(R.id.name);
        job = (EditText)rootView.findViewById(R.id.jobTitle);
        about = (EditText)rootView.findViewById(R.id.aboutMeText);
        friends = (EditText)rootView.findViewById(R.id.friends);
        profilePicture = (ImageView) rootView.findViewById(R.id.profileImg);
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

                String[] outputStrArr = new String[selectedItems.size()];

                for (int i = 0; i < selectedItems.size(); i++) {
                    outputStrArr[i] = selectedItems.get(i);
                }

                String userName = (String) name.getText().toString();
                String userJob = (String) job.getText().toString();
                String userAbout = (String) about.getText().toString();
                String userFriends = (String) friends.getText().toString();
                // Create a bundle object
                Bundle b = new Bundle();
                b.putStringArray("selectedItems", outputStrArr);

                b.putString("userName", userName);
                b.putString("userJob", userJob);
                b.putString("userAbout", userAbout);
                b.putString("userFriends", userFriends);

                String[] resultArr = b.getStringArray("selectedItems");
                JsonUtil json = new JsonUtil();
                try {
                   String jsonStr =  json.toJSon(b);
                    visit = true;
                    editor.putString("profileInfo",jsonStr);
                    editor.putBoolean("visit", visit);
                    editor.commit();
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

}




