package com.example.medicinedonator.User.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicinedonator.R;
import com.example.medicinedonator.User.Activities.MainActivity;
import com.example.medicinedonator.User.Core.Interface;
import com.example.medicinedonator.User.Core.RecyclerData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {

    ArrayAdapter<String> adapterDistrict;

    Spinner etDistrict;

    EditText etFname, etSurename, etEmail, etPhone, etAddress, etPassword, etReenterpw;
    TextView txtSignup;

    LoginFragment loginFragment = new LoginFragment();


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
        etDistrict = view.findViewById(R.id.etDistrict);
        etAddress = view.findViewById(R.id.etAddress);
        etPassword = view.findViewById(R.id.etPasswordOne);
        etReenterpw = view.findViewById(R.id.etReenterPassword);
        txtSignup = view.findViewById(R.id.txtSIGNUP);

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });


        return view;
    }


    public void Validation() {
        if (etPhone.getText().toString().length() !=10 ) {
            Toast.makeText(getContext(), "Enter valid mobile number", Toast.LENGTH_SHORT).show();
        }
        else if (etFname.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter first name", Toast.LENGTH_SHORT).show();
        } else if (etSurename.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter Last name", Toast.LENGTH_SHORT).show();
        } else if (etEmail.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter email", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(etEmail.getText().toString())) {
            Toast.makeText(getContext(), "Enter valid email", Toast.LENGTH_SHORT).show();
        }
        else if (etAddress.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter Address", Toast.LENGTH_SHORT).show();
        } else if (etDistrict.getSelectedItem().toString().equals("District")) {
            Toast.makeText(getContext(), "Select District", Toast.LENGTH_SHORT).show();
        }
        else if (!etPassword.getText().toString().equals(etReenterpw.getText().toString())) {
            Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else if (etPassword.getText().toString().length() < 8 && !isValidPassword(etPassword.getText().toString())) {
            Toast.makeText(getContext(), "Password must contain minimum 8 characters at least 1 Alphabet, 1 Number and 1 Special Character", Toast.LENGTH_SHORT).show();
        }
        else {
            AddMedicine();
        }


    }

    private void AddMedicine() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Signing_in...");
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
        String category = "user";
        JSONObject object = new JSONObject();
        JsonObject object1 = new JsonObject();
        try {
            object.put("FirstName", etFname.getText().toString());
            object.put("LastName", etSurename.getText().toString());
            object.put("Email", etEmail.getText().toString());
            object.put("Phone", etPhone.getText().toString());
            object.put("District", etDistrict.getSelectedItem().toString());
            object.put("Address", etAddress.getText().toString());
            object.put("Category", category);
            object.put("Password", etPassword.getText().toString());

            Toast.makeText(getContext(), category, Toast.LENGTH_SHORT).show();
            JsonParser parser  = new JsonParser();
            object1 =(JsonObject) parser.parse(object.toString());

        } catch (Exception ex) {

        }


        Interface retrofitAPI = retrofit.create(Interface.class);
        Call<RecyclerData> call = retrofitAPI.addUser(object1);
        call.enqueue(new Callback<RecyclerData>() {
            @Override
            public void onResponse(Call<RecyclerData> call, Response<RecyclerData> response) {
                if (response.body()!=null) {
                    progressDialog.dismiss();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, loginFragment).commit();

                }
            }

            @Override
            public void onFailure(Call<RecyclerData> call, Throwable t) {
                // displaying an error message in toast
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}