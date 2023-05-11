package com.example.medicinedonator.User.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.medicinedonator.R;
import com.example.medicinedonator.User.Core.Interface;
import com.example.medicinedonator.User.Core.RecyclerData;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class RegisterFragment extends Fragment {
    ArrayAdapter<String> adapterDistrict;

    Spinner etDistrict;

    EditText etFname,etSurename,etEmail,etPhone,etAddress,etCategory,etPassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        etFname = view.findViewById(R.id.etFirstname);

        etSurename = view.findViewById(R.id.etLastname);
        etEmail = view.findViewById(R.id.etEmail);
        etPhone = view.findViewById(R.id.etPhonenumber);
        etAddress = view.findViewById(R.id.etAddress);

        etPassword = view.findViewById(R.id.etPasswordOne);


        getCourse();
        return view;
    }


    private void getCourse() {

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

        Interface retrofitAPI = retrofit.create(Interface.class);
        Call<RecyclerData> call = retrofitAPI.getFirstName();
        call.enqueue(new Callback<RecyclerData>() {
            @Override
            public void onResponse(Call<RecyclerData> call, Response<RecyclerData> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "asdasda", Toast.LENGTH_SHORT).show();
                    // inside the on response method.
                    // we are hiding our progress bar.
                  //  loadingPB.setVisibility(View.GONE);
                    // in below line we are making our card
                    // view visible after we get all the data.
                   // courseCV.setVisibility(View.VISIBLE);
                    RecyclerData modal = response.body();
                    // after extracting all the data we are
                    // setting that data to all our views.
                    etFname.setText(modal.getFirstName());
                    etSurename.setText(modal.getLastName());
                    etAddress.setText(modal.getAddress());
                    // we are using picasso to load the image from url.
                    //Picasso.get().load(modal.getCourseimg()).into(courseIV);
                }
            }

            @Override
            public void onFailure(Call<RecyclerData> call, Throwable t) {
                // displaying an error message in toast
                Toast.makeText(getContext(), "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}