package com.tugasmoop.projectsoftwareengineering.UI.adapter;

import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUsername;
import static com.tugasmoop.projectsoftwareengineering.SharedData.statusTxt;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tugasmoop.projectsoftwareengineering.R;
import com.tugasmoop.projectsoftwareengineering.data.models.Transaction;
import com.tugasmoop.projectsoftwareengineering.TransactionHistory;

import java.util.List;

public class AdapterDataHistory extends RecyclerView.Adapter<AdapterDataHistory.HolderData> {

    List<Transaction> listDataAdapter;
    LayoutInflater inflater; //Masukin tampilan, nge inflate
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fixedofficial-f94c9-default-rtdb.firebaseio.com/");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference;
    public static String currentHistoryServicer;
    public static boolean payed = false;
    public static int idOrderCustomer, idOrderServicer;
    public static int historyPos = 0;
    Context context;

    public AdapterDataHistory(Context context, List<Transaction> listData) {
        Log.d("TEST114", "masuk");
        this.context = context;
        Log.d("TEST115", "masuk");
        this.listDataAdapter = listData;
        Log.d("TEST116", "masuk");
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //menempatkan data ke tampilan (inflate)
        View view = inflater.inflate(R.layout.template_history, parent, false);//parent --> context
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        //currentServicer
        String currentServicer = listDataAdapter.get(position).getServicerUsername();
//        //set hasil
        databaseReference.child("Orders").child(currentUsername).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String[] strMonth = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Ags", "Sept", "Okt", "Nov", "Dec"};
                int day = snapshot.child(String.valueOf(holder.getPosition())).child("day").getValue(Integer.class);
                int month = snapshot.child(String.valueOf(holder.getPosition())).child("month").getValue(Integer.class);
                int year = snapshot.child(String.valueOf(holder.getPosition())).child("year").getValue(Integer.class);
                int hour = snapshot.child(String.valueOf(holder.getPosition())).child("hour").getValue(Integer.class);
                int minute = snapshot.child(String.valueOf(holder.getPosition())).child("minute").getValue(Integer.class);
                idOrderCustomer = snapshot.child(String.valueOf(holder.getPosition())).child("idOrderCustomer").getValue(Integer.class);

                databaseReference.child("Users").child(currentServicer).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String category = snapshot.child("servicer").child("category").getValue(String.class);
                        String phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
                        String servicerName = snapshot.child("name").getValue(String.class);

                        holder.phoneServicerHistory.setText("Servicer phone number : " + phoneNumber);
                        holder.categoryHistory.setText("Category : " + category);
                        holder.nameServicerHistory.setText("Servicer name : " + servicerName);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                statusTxt = snapshot.child(String.valueOf(holder.getPosition())).child("status").getValue(Integer.class);

                Log.d("TESTStatus", "lewatin status");

                if(statusTxt == -1){
                    holder.waiting.setImageResource(R.drawable.ic_baseline_access_time_24);
                    Log.d("TESTStatus", "masuk - 1");
                }
                else if(statusTxt == 8){
                    holder.waiting.setVisibility(View.INVISIBLE);
                    Log.d("TESTStatus", "masuk 8");
                }

                Log.d("TESTStatus", "lewatin If");

                Log.d("TESTStatus", String.valueOf(day) + " " + strMonth[month - 1] + " " + year + " at " + String.format("%02d:%02d", hour, minute));
                String dateAndTime = String.valueOf(day) + " " + strMonth[month - 1] + " " + year + " at " + String.format("%02d:%02d", hour, minute);
                holder.dateTimeHistory.setText(dateAndTime);
                holder.statusHistory.setText("Status : " + (statusTxt == 0 ? "Pending" : statusTxt == 8 ? "Ongoing" : statusTxt == 1 ? "Finish" : statusTxt == -1 ? "Checking" : "Canceled"));
                // checking = -1, 0 pending, 8 lagi dikerjain, 1 finish, -99 cancel
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.boxHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("testPos", String.valueOf(holder.getPosition()));
                databaseReference.child("Orders").child(currentUsername).child(String.valueOf(holder.getPosition())).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        idOrderServicer = snapshot.child("idOrderServicer").getValue(Integer.class);
                        historyPos = holder.getPosition();
                        if(statusTxt == 0){
                            payed = false;
                        }else if(statusTxt == 8 || statusTxt == 1 || statusTxt == -1){
                            payed = true;
                        }
                        currentHistoryServicer = listDataAdapter.get(holder.getPosition()).getServicerUsername();
                        Intent detail = new Intent(context, TransactionHistory.class);
                        context.startActivity(detail);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDataAdapter.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{

        TextView dateTimeHistory, categoryHistory, statusHistory, nameServicerHistory, phoneServicerHistory;
        RelativeLayout boxHistory;
        ImageView waiting;

        public HolderData(@NonNull View itemView) {
            super(itemView);//parent
            dateTimeHistory = itemView.findViewById(R.id.dateTimeHistory);
            categoryHistory = itemView.findViewById(R.id.categoryHistory);
            statusHistory = itemView.findViewById(R.id.statusHistory);
            nameServicerHistory = itemView.findViewById(R.id.nameServicerHistory);
            phoneServicerHistory = itemView.findViewById(R.id.phoneServicerHistory);
            boxHistory = itemView.findViewById(R.id.boxHistory);
            waiting = itemView.findViewById(R.id.waiting);
            Log.d("TEST118", "masuk");
        }
    }
}
