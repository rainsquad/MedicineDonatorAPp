package com.example.medicinedonator.User.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.medicinedonator.R;
import com.example.medicinedonator.User.Activities.MainActivity;
import com.example.medicinedonator.User.Core.AdapterMedicineList;
import com.example.medicinedonator.User.Core.CategoryResponseModel;
import com.example.medicinedonator.User.Core.Interface;
import com.example.medicinedonator.User.Core.Medicine;
import com.example.medicinedonator.User.Core.RecyclerData;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private List<Medicine> medicines;

    private AdapterMedicineList adapterMedicineList;
    private RecyclerView.LayoutManager layoutManager;

    RecyclerView recyclerView,recyclerView2,recyclerView3,recyclerView4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView2 = view.findViewById(R.id.recyclerView2);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setHasFixedSize(true);

        recyclerView3 = view.findViewById(R.id.recyclerView3);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView3.setLayoutManager(layoutManager);
        recyclerView3.setHasFixedSize(true);

        recyclerView4 = view.findViewById(R.id.recyclerView4);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView4.setLayoutManager(layoutManager);
        recyclerView4.setHasFixedSize(true);









        getCourse();
        return view;

    }

    private void getCourse() {


        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Getting data...");
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


//        Interface retrofitAPI = retrofit.create(Interface.class);
//        Call<List<Medicine>> call = retrofitAPI.getAllMedicine();
//        call.enqueue(new Callback<List<Medicine>>() {
//            @Override
//            public void onResponse(Call<List<Medicine>> call, Response<List<Medicine>> response) {
//                medicines = response.body();
//
//                adapterMedicineList = new AdapterMedicineList(medicines, getContext());
//
//                medicines = new ArrayList<>();
//
//
//                //   AdapterMedicineList adapter = new AdapterMedicineList(medicines, getContext());
//                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
//                recyclerView.setLayoutManager(layoutManager);
//
//
//                recyclerView.setAdapter(adapterMedicineList);
//                adapterMedicineList.notifyDataSetChanged();
//                Toast.makeText(getContext(), "awaaaa", Toast.LENGTH_SHORT).show();
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Medicine>> call, Throwable t) {
//                // displaying an error message in toast
//                Toast.makeText(getContext(), "Fail to get the data..", Toast.LENGTH_SHORT).show();
//            }
//        });

        Interface retrofitAPI = retrofit.create(Interface.class);
        Call<CategoryResponseModel> call = retrofitAPI.getItemsonCategory();
        call.enqueue(new Callback<CategoryResponseModel>() {
            @Override
            public void onResponse(Call<CategoryResponseModel> call, Response<CategoryResponseModel> response) {

//Ears Nose Medicine list
                adapterMedicineList = new AdapterMedicineList(response.body().getENT(), getContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapterMedicineList);


//Central Nervous System Medicine
                adapterMedicineList = new AdapterMedicineList(response.body().getCNS(), getContext());
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
                recyclerView2.setLayoutManager(layoutManager1);
                recyclerView2.setAdapter(adapterMedicineList);




//Muscles and joints medicine list
                adapterMedicineList = new AdapterMedicineList(response.body().getMuscles(), getContext());
                LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
                recyclerView3.setLayoutManager(layoutManager3);
                recyclerView3.setAdapter(adapterMedicineList);





//Heart Medicine List
                adapterMedicineList = new AdapterMedicineList(response.body().getHeart(), getContext());
                LinearLayoutManager layoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
                recyclerView4.setLayoutManager(layoutManager4);
                recyclerView4.setAdapter(adapterMedicineList);
                adapterMedicineList.notifyDataSetChanged();

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<CategoryResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), "Cannot retrive data!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });



    }


}