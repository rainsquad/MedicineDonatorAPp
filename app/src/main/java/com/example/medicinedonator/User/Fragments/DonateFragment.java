package com.example.medicinedonator.User.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicinedonator.R;
import com.example.medicinedonator.User.Core.DonateResponseModel;
import com.example.medicinedonator.User.Core.Interface;
import com.example.medicinedonator.User.Core.Medicine;
import com.example.medicinedonator.User.Core.RecyclerData;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DonateFragment extends Fragment {

    TextView select_umage;

    ImageView txtAdd;
    EditText Name,Description,Quantity,MFD,EXD;
    private Bitmap bitmap;

    AppCompatSpinner    Category;

    HomeFragment homeFragment = new HomeFragment();

    final Calendar myCalendar= Calendar.getInstance();
    ImageView  upload_image;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donate, container, false);

        upload_image = view.findViewById(R.id.upload_img);
        select_umage = view.findViewById(R.id.txtupload_image);
        Name = view.findViewById(R.id.Name);
        Description = view.findViewById(R.id.Description);
        Category = view.findViewById(R.id.Category);
        Quantity = view.findViewById(R.id.Quantity);
        MFD = view.findViewById(R.id.MFD);
        EXD = view.findViewById(R.id.ExpDate);
        txtAdd = view.findViewById(R.id.txtAdd);


        DatePickerDialog.OnDateSetListener mfd =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabelMFD();

            }
        };
        DatePickerDialog.OnDateSetListener exd =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                updateLabelEXD();
            }
        };
        MFD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),mfd,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        EXD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),exd,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        SetupSpinner();

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseFile();

            }
        });
        select_umage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if (Name.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Enter name", Toast.LENGTH_SHORT).show();
                } else if (Description.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Enter Description", Toast.LENGTH_SHORT).show();
                } else if (Category.getSelectedItem().toString().equals("")) {
                    Toast.makeText(getContext(), "Select Category", Toast.LENGTH_SHORT).show();
                }
                else if (Quantity.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Enter Quantity", Toast.LENGTH_SHORT).show();
                } else if (MFD.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Enter Manufacture Date", Toast.LENGTH_SHORT).show();
                } else if (EXD.getText().toString().equals("") ) {
                    Toast.makeText(getContext(), "Enter Expire Date", Toast.LENGTH_SHORT).show();
                } else {
                    String picture = null;
                    if (bitmap == null) {
                        Toast.makeText(getContext(), "Select an Image", Toast.LENGTH_SHORT).show();

                    } else {
                        picture = getStringImage(bitmap);
                        // picture = getRoundedCroppedBitmap(bitmap);
                        getCourse(picture);
                    }


                }


            }
        });


        return view;
    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);

                upload_image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private void getCourse(String pic) {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Uploading...");
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
        String user = "ajith";
//        String ProPic = "ajith";
        JSONObject object = new JSONObject();
        JsonObject object1 = new JsonObject();
        try {
            object.put("Name", Name.getText().toString());
            object.put("Description", Description.getText().toString());
            object.put("Category", Category.getSelectedItem().toString());
            object.put("Quantity",  Integer.parseInt(Quantity.getText().toString()));
            object.put("MFD", MFD.getText().toString());
            object.put("ExpDate", EXD.getText().toString());
            object.put("Picture",pic);
            object.put("User","6459a6ea2698fad97c0e4363");
            JsonParser parser  = new JsonParser();
            object1 =(JsonObject) parser.parse(object.toString());


            System.out.println(object1+"Whucana object ne mewa...amor amorr");
        } catch (Exception ex) {

        }


        Interface retrofitAPI = retrofit.create(Interface.class);
        Call<DonateResponseModel> call = retrofitAPI.addMedicine(object1);

        call.enqueue(new Callback<DonateResponseModel>() {
            @Override
            public void onResponse(Call<DonateResponseModel> call, Response<DonateResponseModel>response) {
                if (response.body()!= null) {

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    Toast.makeText(getContext(), "Successfull", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DonateResponseModel> call, Throwable t) {
                // displaying an error message in toast
                Toast.makeText(getContext(), "Fail to get the data..", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        progressDialog.dismiss();

    }

    private void SetupSpinner() {
        ArrayList<String> postpartumStageList = new ArrayList<>();
        postpartumStageList.add("Select Category");
        postpartumStageList.add("Heart");
        postpartumStageList.add("Ear,Nose & Throat");
        postpartumStageList.add("Muscles & Joints");
        postpartumStageList.add("Central nervous System");
        ArrayAdapter<String> postpartumStageAdapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,postpartumStageList);
        Category.setAdapter(postpartumStageAdapter);
    }
    private void updateLabelMFD(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        MFD.setText(dateFormat.format(myCalendar.getTime()));
    }
    private void updateLabelEXD(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        EXD.setText(dateFormat.format(myCalendar.getTime()));
    }
}