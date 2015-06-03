package com.gimbal.sample;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LogIn extends Activity {
    public static String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.log_in, menu);
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
        // Shared preferences name
        public static String PREFS_NAME=null;

        String message;

        EditText email;
        EditText password;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_log_in, container, false);

            // Storing username as file name
            Bundle extras = getActivity().getIntent().getExtras();
            if (extras != null) {
                value = extras.getString("registered_email");
            }

            // Log In
            Button buttonLogIn = (Button) rootView.findViewById(R.id.button);

            // Capturing user details
            email = (EditText) rootView.findViewById(R.id.editText3);
            password = (EditText) rootView.findViewById(R.id.editText4);

            buttonLogIn.setOnClickListener(new View.OnClickListener(){
                // On Click Action
                @Override
                public void onClick(View view) {
                    // Fetching user registration details in text
                    String emailStore = value;
                    String passwordStore = password.getText().toString().trim();

                    // Mandatory Validation
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
                        String passwordPref = prefs.getString("password", null);

                        if (passwordStore.equals(passwordPref)) {
                            // Start the activity of Profile Setup
                            Intent proximityActivity = new Intent().setClass(getActivity(), ProximityActivity.class);
                            startActivity(proximityActivity);

                            //Intent jsonActivity = new Intent().setClass(getActivity(), MyActivity.class);
                            //startActivity(jsonActivity);

                        } else if (!passwordStore.equals(passwordPref)) {
                            // Display Toast Message
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Incorrect Username or Password", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();
                        }
                    }
                }
            });

            return rootView;
        }
    }
}
