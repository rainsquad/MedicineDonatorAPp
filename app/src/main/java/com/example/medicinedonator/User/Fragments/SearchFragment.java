package com.example.medicinedonator.User.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.medicinedonator.R;
import com.example.medicinedonator.User.Activities.MainActivity;
import com.example.medicinedonator.User.Core.AdapterMedicineList;
import com.example.medicinedonator.User.Core.AdapterMedicineListSearch;
import com.example.medicinedonator.User.Core.CategoryResponseModel;
import com.example.medicinedonator.User.Core.Interface;
import com.example.medicinedonator.User.Core.Medicine;
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


public class SearchFragment extends Fragment {
    RecyclerView recyclerView;
    private List<Medicine> medicines;

    EditText searcheItem;
    ImageView search;
    private AdapterMedicineListSearch adapterMedicineListSearch;

    private RecyclerView.LayoutManager layoutManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        search = view.findViewById(R.id.imgSearch);
        searcheItem = view.findViewById(R.id.txt1);


        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        getMedicine();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSearchedItem();
            }
        });



        return view;
    }


    private void getMedicine() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Getting Items..");
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


        Interface retrofitAPI = retrofit.create(Interface.class);
        Call<List<Medicine>> call = retrofitAPI.getAllMedicine();
        call.enqueue(new  Callback<List<Medicine>>() {
            @Override
            public void onResponse( Call<List<Medicine>> call, Response<List<Medicine>> response) {

//Ears Nose Medicine list
                medicines = response.body();
                adapterMedicineListSearch = new AdapterMedicineListSearch(medicines, getContext());
                recyclerView.setAdapter(adapterMedicineListSearch);
                adapterMedicineListSearch.notifyDataSetChanged();
            progressDialog.dismiss();
            }

            @Override
            public void onFailure( Call<List<Medicine>> call, Throwable t) {
progressDialog.dismiss();
                Toast.makeText(getContext(), "Failed to get items", Toast.LENGTH_SHORT).show();
            }
        });




    }
    private void getSearchedItem() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Getting Items..");
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
            object.put("Name", searcheItem.getText().toString());
            JsonParser parser  = new JsonParser();
            object1 =(JsonObject) parser.parse(object.toString());

        } catch (Exception ex) {

        }


        Interface retrofitAPI = retrofit.create(Interface.class);
        Call<List<Medicine>> call = retrofitAPI.getSearcedItem(object1);
        call.enqueue(new Callback<List<Medicine>>() {
            @Override
            public void onResponse(Call<List<Medicine>> call, Response<List<Medicine>> response) {
                if (response.isSuccessful()) {



                    medicines = response.body();
                    adapterMedicineListSearch = new AdapterMedicineListSearch(medicines, getContext());
                    recyclerView.setAdapter(adapterMedicineListSearch);
                    adapterMedicineListSearch.notifyDataSetChanged();
                    progressDialog.dismiss();


                }
            }

            @Override
            public void onFailure(Call<List<Medicine>> call, Throwable t) {
                // displaying an error message in toast
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}