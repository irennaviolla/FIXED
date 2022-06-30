package com.tugasmoop.projectsoftwareengineering.UI.adapter;

import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUser;
import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUsername;
import static com.tugasmoop.projectsoftwareengineering.SharedData.statusTxt;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.List;

public class AdapterDataOrder extends RecyclerView.Adapter<AdapterDataOrder.HolderData> {

    List<Transaction> listDataAdapter;
    LayoutInflater inflater; //Masukin tampilan, nge inflate
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fixedofficial-f94c9-default-rtdb.firebaseio.com/");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference;
    public static String currentDetailServicer;
    int idOrderCustomer = 0, idOrderServicer = 0;
    Context context;


    public AdapterDataOrder(Context context, List<Transaction> listData) {
        this.context = context;
        if(listData.size() == 0) return;
        this.listDataAdapter = listData;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //menempatkan data ke tampilan (inflate)
        View view = inflater.inflate(R.layout.template_order_servicer, parent, false);//parent --> context
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        //currentServicer
        String currentCustomer = listDataAdapter.get(position).getCustomerUsername();
        Log.d("TESTLAGI", currentCustomer + ", position: " + position + ", currentUsername" + currentUsername);
//        //set hasil
        //AKAN ADA MASALAH JIKA USER MELAKUKAN CANCEL KARENA POSITION BERUBAH
        databaseReference.child("Users").child(currentUsername).child("servicer").child("orders").child(String.valueOf(position)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("TESTLAGI", "TEST MASUK");
                String[] strMonth = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Ags", "Sept", "Okt", "Nov", "Dec"};
                int day = snapshot.child("day").getValue(Integer.class);
                int month = snapshot.child("month").getValue(Integer.class);
                int year = snapshot.child("year").getValue(Integer.class);
                int hour = snapshot.child("hour").getValue(Integer.class);
                int minute = snapshot.child("minute").getValue(Integer.class);

                Log.d("TESTLAGI1.1", "TEST MASUK");

                idOrderCustomer = snapshot.child("idOrderCustomer").getValue(Integer.class);
                idOrderServicer = snapshot.child("idOrderServicer").getValue(Integer.class);

                Log.d("TESTLAGI1.2", "TEST MASUK");

                databaseReference.child("Users").child(currentCustomer).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue(String.class);
                        String address = snapshot.child("address").getValue(String.class);
                        String phone = snapshot.child("phoneNumber").getValue(String.class);

                        Log.d("TESTLAGI3", "TEST MASUK");

                        holder.nameOrder.setText("Customer name : " + name);
                        Log.d("TESTLAGI4", "TEST MASUK");
                        holder.addressOrder.setText("Customer address : " + address);
                        Log.d("TESTLAGI5", "TEST MASUK");
                        holder.phoneOrder.setText("Customer phone number : " + phone);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Log.d("TESTLAGI2", "TEST MASUK");

                statusTxt = snapshot.child("status").getValue(Integer.class);

                Log.d("TEST LAGI", "TEST MASUK, status = " + statusTxt);

                String dateAndTime = String.valueOf(day) + " " + strMonth[month] + " " + year + " at " + String.format("%02d:%02d", hour, minute);
                holder.dateTimeOrder.setText(dateAndTime);
                holder.statusOrder.setText("Status : " + (statusTxt == 0 ? "Pending" : statusTxt == 8 ? "Ongoing" : statusTxt == 1 ? "Finish" : statusTxt == -1 ? "Checking" : "Canceled"));
                // checking = -1, 0 pending, 8 lagi dikerjain, 1 finish, -99 cancel
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.doneOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUser.getServicer().getTransactionList().get(holder.getPosition()).setStatus(1);
                databaseReference.child("Users").child(currentUsername).child("servicer").child("orders").child(String.valueOf(idOrderServicer)).child("status").setValue(1);
                databaseReference.child("Orders").child(currentCustomer).child(String.valueOf(idOrderCustomer)).child("status").setValue(1);
            }
        });

//        holder.boxOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(statusTxt == 0){
//                    Intent detail = new Intent(context, TransactionHistory.class);
//                    context.startActivity(detail);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listDataAdapter.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{

        TextView dateTimeOrder, statusOrder, nameOrder, addressOrder, phoneOrder;
        RelativeLayout boxOrder;
        Button doneOrder;

        public HolderData(@NonNull View itemView) {
            super(itemView);//parents
            dateTimeOrder = itemView.findViewById(R.id.dateTimeOrder);
            statusOrder = itemView.findViewById(R.id.statusOrder);
            nameOrder = itemView.findViewById(R.id.nameOrder);
            addressOrder = itemView.findViewById(R.id.addressOrder);
            phoneOrder = itemView.findViewById(R.id.phoneOrder);
            boxOrder = itemView.findViewById(R.id.boxOrder);
            doneOrder = itemView.findViewById(R.id.doneOrder);
            Log.d("TEST118", "masuk");
        }
    }
}
