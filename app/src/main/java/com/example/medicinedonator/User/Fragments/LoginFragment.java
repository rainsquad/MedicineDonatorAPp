package com.example.medicinedonator.User.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicinedonator.R;
import com.example.medicinedonator.User.Activities.MainActivity;
import com.example.medicinedonator.User.Core.Constants;
import com.example.medicinedonator.User.Core.Interface;
import com.example.medicinedonator.User.Core.RecyclerData;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginFragment extends Fragment {
    EditText etEmail,etPassword;
    TextView login;

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        login = view.findViewById(R.id.txtLOGIN);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCourse();
            }
        });


        return view;
    }


    private void getCourse() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loggin-In...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://medicine-donate.onrender.com/")
                // on below line we are calling add Converter
                // factory as GSON converter factory.
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.

        JSONObject object = new JSONObject();
        JsonObject object1 = new JsonObject();
        try {
            object.put("Email", etEmail.getText().toString());
            object.put("Password", etPassword.getText().toString());
            JsonParser parser  = new JsonParser();
            object1 =(JsonObject) parser.parse(object.toString());

        } catch (Exception ex) {

        }


        Interface retrofitAPI = retrofit.create(Interface.class);
        Call<List<RecyclerData>> call = retrofitAPI.getFirstName(object1);
        call.enqueue(new Callback<List<RecyclerData>>() {
            @Override
            public void onResponse(Call<List<RecyclerData>> call, Response<List<RecyclerData>> response) {
                if (response.isSuccessful()) {
                  progressDialog.dismiss();

//                    SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString(Constants.USER_FIRST_NAME,);
//                    editor.putString(Constants.KEY_ID, responseBody.getUserDetailObject().getUserDetails().get(0).getId() );
                    RecyclerData modal = response.body().get(1);
                    String USER_ID =   modal.get_id();
                    String USER_NAME = modal.getFirstName();


                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("userid",USER_ID);
                    intent.putExtra("firstname",USER_NAME);

                    startActivity(intent);
                    // inside the on response method.
                    // we are hiding our progress bar.
                    //  loadingPB.setVisibility(View.GONE);
                    // in below line we are making our card
                    // view visible after we get all the data.
                    // courseCV.setVisibility(View.VISIBLE);

                    // after extracting all the data we are
                    // setting that data to all our views.


//                    etAddress.setText(modal.getAddress());
                    // we are using picasso to load the image from url.
                    //Picasso.get().load(modal.getCourseimg()).into(courseIV);
                }
            }

            @Override
            public void onFailure(Call<List<RecyclerData>> call, Throwable t) {
                // displaying an error message in toast
                Toast.makeText(getContext(), "Fail to get the data..", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

}