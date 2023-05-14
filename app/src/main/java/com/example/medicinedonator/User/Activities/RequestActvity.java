package com.example.medicinedonator.User.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicinedonator.R;
import com.example.medicinedonator.User.Core.Interface;
import com.example.medicinedonator.User.Core.Medicine;
import com.example.medicinedonator.User.Core.RecyclerData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestActvity extends AppCompatActivity {

    TextView Name,Desc,Qty,Exp,Category,Mfd;

    ImageView Back;

    TextInputEditText reqQty;

    AppCompatButton Request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_actvity);
        Name = findViewById(R.id.Name);
        Desc = findViewById(R.id.Desc);
        Qty = findViewById(R.id.Qty);
        Exp = findViewById(R.id.exd);
        Category = findViewById(R.id.Cat);
        Mfd = findViewById(R.id.mfd);
        Back = findViewById(R.id.imgBack);
        Request = findViewById(R.id.btnRequest);
        reqQty = findViewById(R.id.etQty);

        Intent i = getIntent();
        String name = i.getStringExtra("Name");
        String desc = i.getStringExtra("Description");
        //String qty = i.getStringExtra("Quantity");
        String exp = i.getStringExtra("ExpDate");
        String category = i.getStringExtra("Category");
        String mfd = i.getStringExtra("MFD");
        int qty = i.getIntExtra("Quantity",0);
        String id = i.getStringExtra("id");

        Name.setText(name);
        Desc.setText(desc);
        Qty.setText(String.valueOf(qty));
        Exp.setText(exp);
        Category.setText(category);
        Mfd.setText(mfd);


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             int b=   Integer.parseInt(String.valueOf(reqQty.getText().toString()));

                UpdateMedicine(qty,id,b);

            }
        });

    }

    private void UpdateMedicine(int a,String id,int b) {
        final ProgressDialog progressDialog = new ProgressDialog(RequestActvity.this);
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

        JSONObject object = new JSONObject();
        JsonObject object1 = new JsonObject();
        try {
            int qtyFinal = a - b;

            object.put("Quantity", qtyFinal );
            object.put("_id", id);

            System.out.println(object);
            Toast.makeText(RequestActvity.this, id, Toast.LENGTH_SHORT).show();
            JsonParser parser  = new JsonParser();
            object1 =(JsonObject) parser.parse(object.toString());

        } catch (Exception ex) {

        }


        Interface retrofitAPI = retrofit.create(Interface.class);
        Call<Medicine> call = retrofitAPI.updateMedicine(object1);
        call.enqueue(new Callback<Medicine>() {
            @Override
            public void onResponse(Call<Medicine> call, Response<Medicine> response) {
                if (response.body()!=null) {
                    Toast.makeText(RequestActvity.this, "Done", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();


                }
            }

            @Override
            public void onFailure(Call<Medicine> call, Throwable t) {
                // displaying an error message in toast
                progressDialog.dismiss();
                Toast.makeText(RequestActvity.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}