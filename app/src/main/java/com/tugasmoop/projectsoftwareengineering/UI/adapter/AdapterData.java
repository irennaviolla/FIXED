package com.tugasmoop.projectsoftwareengineering.UI.adapter;

import static com.tugasmoop.projectsoftwareengineering.SharedData.currCategory;
import static com.tugasmoop.projectsoftwareengineering.SharedData.databaseReference;
import static com.tugasmoop.projectsoftwareengineering.SharedData.mode;
import static com.tugasmoop.projectsoftwareengineering.SharedData.storage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.tugasmoop.projectsoftwareengineering.R;
import com.tugasmoop.projectsoftwareengineering.UI.main.category.DetailActivity;

import java.io.File;
import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {

    List<String> listDataAdapter;
    LayoutInflater inflater; //Masukin tampilan, nge inflate
    String nameTxt;
    double rating;
    boolean checkFavorite = false;
    public static String currentDetailServicer;
    int checkPhoto = 0;
    Context context;

    public AdapterData(Context context, List<String> listData) {
        this.context = context;
        this.listDataAdapter = listData;
        if(listData.size() == 0) return;
        this.inflater = LayoutInflater.from(context);
        Log.d("TEST114", "masuk");
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //menempatkan data ke tampilan (inflate)
        View view = inflater.inflate(R.layout.template_category_servicer, parent, false);//parent --> context
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        //currentServicer
        String currentServicer = listDataAdapter.get(position);
        checkFavorite = false;
        //set hasil
        databaseReference.child("Users").child(currentServicer).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nameTxt = snapshot.child("servicer").child("name").getValue(String.class);
                if(snapshot.child("servicer").child("custCount").getValue(Integer.class) != 0){
                    rating = (double) snapshot.child("servicer").child("rating").getValue(Integer.class) / (double) snapshot.child("servicer").child("custCount").getValue(Integer.class);
                }else{
                    rating = 0.0;
                }
                String category;
                checkPhoto = snapshot.child("checkPhoto").getValue(Integer.class);
                try {
                    if (checkPhoto == 1) {
                        final File localFile = File.createTempFile("photo_" + currentServicer, "jpg");
                        Log.d("testImage", currentServicer + "aaaa");
                        storage.child("images/" + currentServicer + "/profile.jpg").getFile(localFile)
                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Uri userUri = Uri.fromFile(localFile);
                                        Log.d("test", "test");
                                        holder.servicerProfile.setImageURI(userUri);
                                    }
                                });
                    } else {
                        Glide.with(context)
                                .load("https://upload.wikimedia.org/wikipedia/commons/7/70/User_icon_BLACK-01.png?20140731221454")
                                .into(holder.servicerProfile);
                    }
                }catch (Exception e){

                }

                if(mode == 1) category = currCategory;
                else category = snapshot.child("servicer").child("category").getValue(String.class);

                holder.servicerName.setText(nameTxt);
                holder.rating.setText("Rating : " + String.format("%.1f", rating) + "/5");
                holder.servicerCategory.setText(category);
                holder.detailServicer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentDetailServicer = currentServicer;
                        context.startActivity(new Intent(context, DetailActivity.class));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listDataAdapter.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{

        TextView servicerName;
        TextView servicerCategory;
        TextView rating;
        ImageView servicerProfile;
        RelativeLayout detailServicer;

        public HolderData(@NonNull View itemView) {
            super(itemView);//parent
            servicerName = itemView.findViewById(R.id.servicerName);
            servicerCategory = itemView.findViewById(R.id.servicerCategory);
            rating = itemView.findViewById(R.id.rating);
            servicerProfile = itemView.findViewById(R.id.servicerProfile);
            detailServicer = itemView.findViewById(R.id.detailServicer);
            Log.d("TEST118", "masuk");
        }
    }
}
