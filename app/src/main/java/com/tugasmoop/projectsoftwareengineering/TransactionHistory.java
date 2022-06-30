package com.tugasmoop.projectsoftwareengineering;

import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUser;
import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUsername;
import static com.tugasmoop.projectsoftwareengineering.SharedData.statusTxt;
import static com.tugasmoop.projectsoftwareengineering.UI.adapter.AdapterDataHistory.currentHistoryServicer;
import static com.tugasmoop.projectsoftwareengineering.UI.adapter.AdapterDataHistory.idOrderCustomer;
import static com.tugasmoop.projectsoftwareengineering.UI.adapter.AdapterDataHistory.idOrderServicer;
import static com.tugasmoop.projectsoftwareengineering.UI.adapter.AdapterDataHistory.payed;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TransactionHistory extends AppCompatActivity implements View.OnClickListener {

    Button bBackHistory, bDonePayment, bSubmitRating;
    TextView servicerFee, totalCost;
    LinearLayout pay;
    RatingBar ratingBar;
    int fee;
    int isRated = -1;
    int userRating = 0;
    int currentRating = 0;
    int customerCount = 0;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fixedofficial-f94c9-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        servicerFee = findViewById(R.id.servicerFee);
        totalCost = findViewById(R.id.totalCost);

        ratingBar = findViewById(R.id.ratingBar);
        bSubmitRating = findViewById(R.id.bSubmitRating);

        databaseReference.child("Users").child(currentHistoryServicer).child("servicer").child("price").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                servicerFee.setText("Rp" + String.valueOf(snapshot.getValue(Integer.class)));
                fee = snapshot.getValue(Integer.class);
                totalCost.setText("TOTAL : Rp" + (fee + 20000));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        databaseReference.child("Users").child(currentHistoryServicer).child("servicer").child("orders").child(String.valueOf(idOrderServicer)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("testServicer", currentHistoryServicer);
                Log.d("testPos", String.valueOf(idOrderServicer));
                Log.d("testRate", String.valueOf(snapshot.child("rate").getValue(Integer.class)));
                isRated = snapshot.child("rate").getValue(Integer.class);


                if(isRated == -1 && statusTxt == 1){
                    ratingBar.setVisibility(View.VISIBLE);
                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                            bSubmitRating.setVisibility(View.VISIBLE);
                            bSubmitRating.setOnClickListener(click->{
                                userRating = (int) ratingBar.getRating();
                                //ubah data-data
                                databaseReference.child("Users").child(currentHistoryServicer).child("servicer").child("orders").child(String.valueOf(idOrderServicer)).child("rate").setValue(1);
                                databaseReference.child("Users").child(currentHistoryServicer).child("servicer").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        //rating ditambah
                                        currentRating = snapshot.child("rating").getValue(Integer.class);
                                        currentRating += userRating;
                                        databaseReference.child("Users").child(currentHistoryServicer).child("servicer").child("rating").setValue(currentRating);
                                        isRated = 1;

                                        //customercount ditambah
                                        customerCount = snapshot.child("custCount").getValue(Integer.class);
                                        customerCount++;
                                        databaseReference.child("Users").child(currentHistoryServicer).child("servicer").child("custCount").setValue(customerCount);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            });
                        }
                    });
                }else{
                    ratingBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bBackHistory = findViewById(R.id.bBackHistory);
        bDonePayment = findViewById(R.id.bDonePayment);

        pay = findViewById(R.id.pay);
        if(payed){
            pay.setVisibility(View.GONE);
            bDonePayment.setVisibility(View.GONE);
        }else{
            pay.setVisibility(View.VISIBLE);
        }

        bBackHistory.setOnClickListener(this);
        bDonePayment.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.bDonePayment)){
//            databaseReference
//            ubah status di database
            Toast.makeText(this, "Your transaction will be checked, please wait.", Toast.LENGTH_SHORT).show();
            databaseReference.child("Users").child(currentHistoryServicer).child("servicer").child("orders").child(String.valueOf(idOrderServicer)).child("status").setValue(-1);
            databaseReference.child("Orders").child(currentUsername).child(String.valueOf(idOrderCustomer)).child("status").setValue(-1);
            currentUser.getTransactionList().get(idOrderCustomer).setStatus(-1);
        }
        finish();
    }
}