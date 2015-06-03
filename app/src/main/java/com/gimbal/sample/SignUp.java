package com.gimbal.sample;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class SignUp extends Activity {
    // Shared preferences name
    public static String PREFS_NAME=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        EditText firstName;
        EditText lastName;
        EditText email;
        EditText password;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

            // Join Now
            Button buttonJoinNow = (Button) rootView.findViewById(R.id.button);

            // Capturing user details
            firstName = (EditText) rootView.findViewById(R.id.editText);
            lastName = (EditText) rootView.findViewById(R.id.editText2);
            email = (EditText) rootView.findViewById(R.id.editText3);
            password = (EditText) rootView.findViewById(R.id.editText4);

            buttonJoinNow.setOnClickListener(new View.OnClickListener(){
                // On Click Action
                @Override
                public void onClick(View view) {
                    // Fetching user registration details in text
                    String firstNameStore = firstName.getText().toString().trim();
                    String lastNameStore = lastName.getText().toString().trim();
                    String emailStore = email.getText().toString().trim();
                    String passwordStore = password.getText().toString().trim();

                    // Check for Mandatory Validation
                    if (firstNameStore.equals("")) {
                        firstName.setError("First name is required!");
                    }
                    if (lastNameStore.equals("")) {
                        lastName.setError("Last name is required!");
                    }
                    if (emailStore.equals("")) {
                        email.setError("Email is required!");
                    }
                    if (passwordStore.equals("")) {
                        password.setError("Password is required!");
                    } else {

                        // Storing username as file name
                        PREFS_NAME = emailStore;

                        // Storing data in Private Shared preferences
                        final SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("firstname", firstNameStore);
                        editor.putString("lastname", lastNameStore);
                        editor.putString("password", passwordStore);
                        editor.putString("email", emailStore);
                        editor.commit();

                        // Start the activity of CompleteProfile
                        Intent logInActivity = new Intent().setClass(getActivity(), LogIn.class);
                        logInActivity.putExtra("registered_email", emailStore);

                        // Clearing text
                        firstName.setText(null);
                        lastName.setText(null);
                        email.setText(null);
                        password.setText(null);

                        // Start the new activity
                        startActivity(logInActivity);
                    }
                }
            });

            return rootView;
        }
    }
}
