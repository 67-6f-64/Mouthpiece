package com.omega.mouthpiece;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class FeedbackFragment extends Fragment {

    private EditText nameUser;
    private String jsonName;
    private EditText descriptionFeedback;
    private String jsonDescr;
    private Spinner optionVal;
    private String jsonOption;
    private String jsonEmail;
    private Button submitButton;
    private Button exitButton;
    private CheckBox anon;
    private Boolean isAnon;
    private EditText email;
    private TextView emailHeader;
    private TextView nameHeader;

    private RequestQueue feedbackRequestQueue;
    private StringRequest feedbackStringRequest;
    private JSONObject jsonBodyParse;
   // private String url = "102.133.170.83:5000/getFeedback";
    private String url = "http://102.133.170.83:5000/addFeedback";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //find all the data in the layout and convert it to variables

        View root = inflater.inflate(R.layout.fragment_feedback, container, false);

        submitButton = root.findViewById(R.id.submitBtn);
        nameUser = root.findViewById(R.id.nameInput);
        descriptionFeedback = root.findViewById(R.id.descriptionFeedback);
        optionVal = root.findViewById(R.id.subjectsDropdown);
        exitButton = root.findViewById(R.id.exitButton);
        anon = root.findViewById(R.id.anonCheck);
        email = root.findViewById(R.id.emailInput);
        isAnon = anon.isChecked();
        emailHeader = root.findViewById(R.id.emailText);
        nameHeader = root.findViewById(R.id.nameOfUser);


        //send JSON data through to the database
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                jsonName = nameUser.getText().toString();
                jsonDescr = descriptionFeedback.getText().toString();
                jsonOption = optionVal.getSelectedItem().toString();
                jsonEmail = email.getText().toString();

                sendJsonFeedback();
                startActivity(new Intent(getContext(), SettingFragment.class));

            }
        });
        //exit button to close the feedback page
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettingFragment.class));
            }
        });
        //enable user to be anonymous and to enter anonymous data
        anon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                            @Override
                                            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                if (isChecked){
                                                    email.setEnabled(false);
                                                    emailHeader.setTextColor(Color.parseColor("#333138"));
                                                    nameHeader.setTextColor(Color.parseColor("#333138"));
                                                    nameUser.setText("Anonymous");
                                                    nameUser.setEnabled(false);
                                                }
                                                else{
                                                    email.setEnabled(true);
                                                    emailHeader.setTextColor(Color.parseColor("#EE1C31"));
                                                    nameHeader.setTextColor(Color.parseColor("#EE1C31"));
                                                    nameUser.setEnabled(true);
                                                }
                                            }
                                        }
        );

        return root;
    }


    //function to make an API call through Volley to the database using user data
    private void sendJsonFeedback() {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        //convert string to JSON data
        try {

            jsonBodyParse = new JSONObject();
            jsonBodyParse.put("email", jsonEmail);
            jsonBodyParse.put("name", jsonName);
            jsonBodyParse.put("subject", jsonOption);
            jsonBodyParse.put("description", jsonDescr);
            final String fbRequestBody = jsonBodyParse.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Make API request and check the server response
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBodyParse,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(), "String Response : "+ response.toString(), Toast.LENGTH_SHORT).show();
                        //resultTextView.setText("String Response : "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error getting response" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //Add the JSON request to the Volley queue
        requestQueue.add(jsonObjectRequest);

    }

}