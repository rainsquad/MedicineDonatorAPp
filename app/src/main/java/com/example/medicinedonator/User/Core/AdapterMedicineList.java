package com.example.medicinedonator.User.Core;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinedonator.R;
import com.example.medicinedonator.User.Activities.RequestActvity;

import java.util.List;


public class AdapterMedicineList extends RecyclerView.Adapter <AdapterMedicineList.MyViewHolder>{

    private List<Medicine> medicines;
    private Context context;




    public AdapterMedicineList(List<Medicine> medicines, Context context) {
        this.medicines = medicines;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_items_medicine, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.Name.setText(""+medicines.get(position).getName());

        holder.Description.setText(""+medicines.get(position).getDescription());
        holder.Category.setText(""+medicines.get(position).getCategory());
        holder.Quantity.setText(""+medicines.get(position).getQuantity());
        holder.ExpDate.setText(""+medicines.get(position).getExpDate());
        holder.MFD.setText(""+medicines.get(position).getMFD());
        holder.Category.setText(""+medicines.get(position).getCategory());
        String encodedImage = medicines.get(position).getPicture();


      //  String base64 = sharedPreferences.getString(KEY_PROFILE_PIC, null);
        byte[] imgdate = Base64.decode(encodedImage, Base64.DEFAULT);
//                 String text = new String(imgdate, StandardCharsets.UTF_8);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(imgdate, 0, imgdate.length);


            holder.Pic.setImageBitmap(decodedByte);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, RequestActvity.class);
                i.putExtra("Name", medicines.get(position).getName());
                i.putExtra("Description",medicines.get(position).getDescription());
                i.putExtra("Quantity",medicines.get(position).getQuantity());
                i.putExtra("ExpDate",medicines.get(position).getExpDate());
                i.putExtra("MFD",medicines.get(position).getMFD());
                i.putExtra("Category",medicines.get(position).getCategory());
                i.putExtra("id",medicines.get(position).get_id());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Name,Description,Category,Quantity,MFD,ExpDate;
        ImageView Pic;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.ViewName);
            Description = itemView.findViewById(R.id.ViewDescription);
            Category = itemView.findViewById(R.id.Category);
            Quantity = itemView.findViewById(R.id.Qty);
            MFD = itemView.findViewById(R.id.MFD);
            ExpDate = itemView.findViewById(R.id.ExpDate);
            Pic = itemView.findViewById(R.id.pic);

        }


    }

}
